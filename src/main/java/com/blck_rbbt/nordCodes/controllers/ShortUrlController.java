package com.blck_rbbt.nordCodes.controllers;

import com.blck_rbbt.nordCodes.DTO.ShortUrlDTO;
import com.blck_rbbt.nordCodes.converters.ShortUrlConverter;
import com.blck_rbbt.nordCodes.entities.ShortUrl;
import com.blck_rbbt.nordCodes.services.ShortUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("api/v1")
class ShortUrlController {
    private ShortUrlService urlService;
    private ShortUrlConverter urlConverter;
    
    @PostMapping
    public ShortUrlDTO createShortUrlFromAuthUser(@RequestParam Long userId, @RequestParam String signature, @Validated @RequestBody ShortUrl url) {
        return urlConverter.entityToDTO(urlService.createShortUrlFromAuthUser(userId, url, signature));
    }
    
    @GetMapping("/{hash}")
    public ResponseEntity<?> redirectToShortUrl(@PathVariable("hash") String hash) {
        return urlService.findUrlByHash(hash);
    }
    
    @GetMapping("/urls/statistic")
    public List<ShortUrl> getUrlsStatistic() {
        return urlService.getStatistic();
    }
    
    @PutMapping("/lifetime/{hash}/{time}")
    public void setLifetimeFromShortUrl(@PathVariable("hash") String shortUrl, @PathVariable("time") Integer time) {
        urlService.setLifetimeFromShortUrl(shortUrl, time);
    }
    
    @DeleteMapping("/urls/delete/{hash}")
    public ResponseEntity<?> deleteUrlByHash(@PathVariable("hash") String hash,
                                @RequestParam Long user_id, @RequestParam String signature) {
       return urlService.deleteShortUrl(hash, user_id, signature);
    }
    
    @Autowired
    public void setUrlService(ShortUrlService urlService) {
        this.urlService = urlService;
    }
    
    @Autowired
    public void setUrlConverter(ShortUrlConverter urlConverter) {
        this.urlConverter = urlConverter;
    }
    
}
