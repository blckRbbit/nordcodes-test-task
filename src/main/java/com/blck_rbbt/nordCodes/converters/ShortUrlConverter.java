package com.blck_rbbt.nordCodes.converters;

import com.blck_rbbt.nordCodes.DTO.ShortUrlDTO;
import com.blck_rbbt.nordCodes.entities.ShortUrl;
import org.springframework.stereotype.Component;

@Component
public class ShortUrlConverter {
    
    public ShortUrl DTOToEntity(ShortUrlDTO dto) {
        return new ShortUrl(dto.getId(), dto.getUrl(), dto.getHash(), dto.getLifetime() ,dto.getCreatedAt(), dto.getUpdatedAt(), dto.getUser(), dto.getNumberOfTransitions());
    }
    
    public ShortUrlDTO entityToDTO(ShortUrl url) {
        return  new ShortUrlDTO(url.getId(), url.getUrl(), url.getHash(), url.getLifetime(), url.getNumberOfTransitions(), url.getCreatedAt(), url.getCreatedAt(), url.getUser());
    }
}
