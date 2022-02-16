package com.blck_rbbt.nordCodes.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.istack.NotNull;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Entity
@Table(name = "urls")
public class ShortUrl {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    
    @NotNull
    @Column(name = "url", nullable = false)
    private String url;
    
    @Column(name = "hash", nullable = false)
    private String hash;
    
    @Column(name = "lifetime")
    private Integer lifetime;
    
    @Enumerated(value = EnumType.STRING)
    @Column(name = "state")
    private State state;
    
    @Column(name = "number_of_transitions")
    private Long numberOfTransitions;
    
    @Column(name = "created_at", columnDefinition = "TIMESTAMP")
    private ZonedDateTime createdAt;
    
    @Column(name = "updated_at", columnDefinition = "TIMESTAMP")
    private ZonedDateTime updatedAt;
    
    @ManyToOne
    @JsonBackReference
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private User user;
    
    public ShortUrl() {
    }
    
    public ShortUrl(Long id, String url, String hash, Integer lifetime, ZonedDateTime createdAt, ZonedDateTime updatedAt,
                    User user, Long numberOfTransitions) {
        this.id = id;
        this.url = url;
        this.hash = hash;
        this.lifetime = lifetime;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.user = user;
        this.numberOfTransitions = numberOfTransitions;
    }
    
    public ShortUrl(String url, String hash, State state, ZonedDateTime createdAt, ZonedDateTime updatedAt, User user) {
        this.url = url;
        this.hash = hash;
        this.state = state;
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
    
    public State getState() {
        return state;
    }
    
    public void setState(State state) {
        this.state = state;
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
