package com.blck_rbbt.nordCodes.utils;

import com.blck_rbbt.nordCodes.entities.ShortUrl;
import com.blck_rbbt.nordCodes.exceptions.UrlValidationException;
import org.apache.commons.text.RandomStringGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class Util {
    private final RandomStringGenerator randomStringGenerator;

    @Value("${utils.url.prefix}")
    private String urlPrefix;
    
    @Value("${utils.url.postfix}")
    private String urlPostfix;
    
    public Util() {
        this.randomStringGenerator = new RandomStringGenerator
                .Builder().filteredBy(Util::isLatinLetterOrDigit)
                .build();
    }
    
    private static boolean isLatinLetterOrDigit(int codePoint) {
        return ('a' <= codePoint && codePoint <= 'z')
                || ('A' <= codePoint && codePoint <= 'Z')
                || ('0' <= codePoint && codePoint <= '9');
    }
    
    public String addPrefixToUrl(String url) {
        String result;
        
        if (url.startsWith(urlPrefix)) {
            result = url;
        } else {
            result = urlPrefix + url;
        }
        return result;
    }
    
    public boolean validateUrl(String originalUrl) {
        String pattern = "^((https?|ftp)\\:\\/\\/)?([a-z0-9]{1})((\\.[a-z0-9-])|([a-z0-9-]))*\\.([a-z]{2,6})(\\/?)$";
        
        String url;
        if (originalUrl.startsWith(urlPrefix)) {
            url = originalUrl;
        } else if (originalUrl.startsWith("http://")) {
            url = addPrefixToUrl(originalUrl.replace("http://", ""));
        } else {
            url = addPrefixToUrl(originalUrl);
        }
        return url != null && !url.equals("") && url.matches(pattern);
    }
    
    public String generateUrl(int length, ShortUrl url) {
        if (validateUrl(url.getUrl())) {
            if (!url.getUrl().startsWith(urlPrefix)) {
                String correctUrl = urlPrefix + url.getUrl();
                url.setUrl(correctUrl);
            }
            StringBuilder shortUrl = new StringBuilder();
            shortUrl.append(randomStringGenerator.generate(length));
            shortUrl.append(urlPostfix);
            return new String(shortUrl);
        } else {
            throw new UrlValidationException("Incorrect original link specified");
        }
        
    }
    
}
