package com.blck_rbbt.nordCodes.services;

import com.blck_rbbt.nordCodes.entities.ShortUrl;
import com.blck_rbbt.nordCodes.entities.State;
import com.blck_rbbt.nordCodes.entities.User;
import com.blck_rbbt.nordCodes.exceptions.AppError;
import com.blck_rbbt.nordCodes.exceptions.ResourceNotFoundException;
import com.blck_rbbt.nordCodes.exceptions.ShortUrlCreationError;
import com.blck_rbbt.nordCodes.exceptions.ShortUrlCreationException;
import com.blck_rbbt.nordCodes.repositories.ShortUrlRepository;
import com.blck_rbbt.nordCodes.utils.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URLDecoder;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


@Service
public class ShortUrlService {
    private BCryptPasswordEncoder passwordEncoder;
    private final ShortUrlRepository urlRepository;
    private UserService userService;
    private final Util util;
    
    @Value("${secret}")
    private String secret;
    
    Logger logger = LoggerFactory.getLogger(ShortUrlService.class.getSimpleName());
    
    @Autowired
    public ShortUrlService(ShortUrlRepository urlRepository, Util util) {
        this.urlRepository = urlRepository;
        this.util = util;
    }
    
    public List<ShortUrl> getStatistic() {
        return urlRepository.findAll();
    }
    
    public ShortUrl createShortUrlFromAuthUser(Long id, ShortUrl url, String signature) {
        User user = userService.findUserById(id).orElseThrow(
                () -> new ResourceNotFoundException("User not found. Id: " + id));
        if (!user.getSignature().equals(signature)) {
            logger.info(String.format("User with id %s entered an invalid key", id));
            throw new ResourceNotFoundException("User with this id and signature was not found");
        }
        return createShortUrl(user.getId(), url);
    }
    
    public ShortUrl createShortUrl(Long userId, ShortUrl shortUrl) {
        try {
            User user = userService.findUserById(userId).orElseThrow((() -> new ResourceNotFoundException("User not found")));
            String hash = util.generateUrl(5, shortUrl);
            String shorterString = URLDecoder.decode(shortUrl.getUrl());
            logger.info(String.format("For the original link %s, a short one was created - %s", shorterString, hash));
            shortUrl = new ShortUrl(shorterString, hash, State.ACTIVE, ZonedDateTime.now(), ZonedDateTime.now(), user);
            shortUrl.setNumberOfTransitions(0L);
            urlRepository.save(shortUrl);
            return shortUrl;
        } catch (ShortUrlCreationException e) {
            throw new ShortUrlCreationException("Error creating short link");
        }
    }
    
    @Transactional
    public ResponseEntity<?> deleteShortUrl(String hash, Long user_id, String signature) {
        User user = userService.findUserById(user_id).orElseThrow(
                () -> new ResourceNotFoundException("User not found. Id: " + user_id));
        ShortUrl url = urlRepository.findByHash(hash).orElseThrow(
                () -> new ResourceNotFoundException("Url with this hash was not found"));
        if (!user.getSignature().equals(signature)) {
            logger.info(String.format("User with id %s entered an invalid key", user_id));
            return new ResponseEntity<AppError>(HttpStatus.UNAUTHORIZED);
        }
        
        if (!user.getId().equals(url.getUser().getId())) {
            logger.info(String.format("The user with id %s is not allowed to delete the link %s", user_id, hash));
            return new ResponseEntity<AppError>(HttpStatus.FORBIDDEN);
        }
        
        try {
            urlRepository.delete(url);
            logger.info(String.format("Link %s deleted by user with id %s", hash, user_id));
            return new ResponseEntity<String>(HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<AppError>(HttpStatus.NOT_FOUND);
        }
    }
    
    @Transactional
    public void setLifetimeFromShortUrl(String hash, Integer lifetime) {
        ShortUrl url = urlRepository.findByHash(hash).orElseThrow(() -> new ResourceNotFoundException("Link not found"));
        if (lifetime >= 1) {
            url.setLifetime(lifetime);
        } else {
            throw new RuntimeException("Link lifetime must be an integer greater than zero");
        }
    }
    
    public String generateUserSignature(ShortUrl url) {
        url = urlRepository.getByHash(url.getHash());
        Map<String, String> parameters = new TreeMap<>();
        parameters.put("id", String.valueOf(url.getId()));
        parameters.put("url", url.getUrl());
        parameters.put("hash", url.getHash());
        parameters.put("lifetime", String.valueOf(url.getLifetime()));
        parameters.put("state", url.getState().name());
        parameters.put("number_of_transitions", String.valueOf(url.getNumberOfTransitions()));
        parameters.put("created_at", String.valueOf(url.getCreatedAt()));
        StringBuilder temp = new StringBuilder();
        for (Map.Entry el : parameters.entrySet()) {
            temp.append(String.format("%s=%s&", el.getKey(), el.getValue()));
        }
        temp.deleteCharAt(temp.length() - 1);
        temp.append(secret);
        String signature = encryptSignature(new String(temp));
        userService.saveUser(signature);
        User user = userService.findUserBySignature(signature).orElseThrow(
                () -> new ResourceNotFoundException("User not found"));
        logger.info(String.format("A unique signature has been created for the user with id %s", user.getId()));
        return signature;
    }
    
    private String encryptSignature(String signature) {
        return passwordEncoder.encode(signature);
    }
    
    @Transactional
    public ResponseEntity<?> findUrlByHash(String hash) {
        HttpHeaders headers = new HttpHeaders();
        ShortUrl url = urlRepository.findByHash(hash).orElseThrow(
                () -> new ResourceNotFoundException("Short link with this hash not found"));
        if (!isLinkAlive(hash)) {
            headers.add("Body", "link has expired");
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        try {
            url.setNumberOfTransitions(url.getNumberOfTransitions() + 1);
            headers.add("Location", url.getUrl());
            logger.info(String.format("Created a new short link: %s", url.getUrl()));
            return new ResponseEntity<String>(headers, HttpStatus.FOUND);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<ShortUrlCreationError>(HttpStatus.NOT_FOUND);
        }
    }
    
    public boolean isLinkAlive(String hash) {
        ShortUrl url = getUrlByHash(hash);
        if (!(url.getLifetime()==null)) {
            ZonedDateTime lifetime = url.getUpdatedAt().plusMinutes(url.getLifetime());
            if (ZonedDateTime.now().isAfter(lifetime)) {
                url.setState(State.DELETED);
            }
        }
        return url.getState().equals(State.ACTIVE);
    }
    
    public ShortUrl getUrlByHash(String hash) {
        return urlRepository.getByHash(hash);
    }
    
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
    
    @Autowired
    public void setPasswordEncoder(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
}
