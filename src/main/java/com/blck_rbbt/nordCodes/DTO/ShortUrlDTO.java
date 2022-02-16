package com.blck_rbbt.nordCodes.DTO;

import com.blck_rbbt.nordCodes.entities.User;

import java.time.ZonedDateTime;

public class ShortUrlDTO {
    private Long id;
    private String url;
    private String hash;
    private Integer lifetime;
    private Long numberOfTransitions;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
    private User user;
    
    public ShortUrlDTO() {
    }
    
    public ShortUrlDTO(Long id, String url, String hash, Integer lifetime, Long numberOfTransitions,  ZonedDateTime createdAt,
                       ZonedDateTime updatedAt, User user) {
        this.id = id;
        this.url = url;
        this.hash = hash;
        this.lifetime = lifetime;
        this.numberOfTransitions = numberOfTransitions;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.user = user;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getUrl() {
        return url;
    }
    
    public void setUrl(String url) {
        this.url = url;
    }
    
    public String getHash() {
        return hash;
    }
    
    public void setHash(String hash) {
        this.hash = hash;
    }
    
    public Integer getLifetime() {
        return lifetime;
    }
    
    public void setLifetime(Integer lifetime) {
        this.lifetime = lifetime;
    }
    
    public Long getNumberOfTransitions() {
        return numberOfTransitions;
    }
    
    public void setNumberOfTransitions(Long numberOfTransitions) {
        this.numberOfTransitions = numberOfTransitions;
    }
    
    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public ZonedDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(ZonedDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
}
