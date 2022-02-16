package com.blck_rbbt.nordCodes.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sun.istack.NotNull;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    
    @NotNull
    @Column(name = "signature", nullable = false)
    private String signature;
    
    @Enumerated(value = EnumType.STRING)
    @Column(name = "role")
    private Role role;
    
    @Enumerated(value = EnumType.STRING)
    @Column(name = "state")
    private State state;
    
    @Column(name = "created_at", columnDefinition = "TIMESTAMP")
    private ZonedDateTime createdAt;
    
    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    private List<ShortUrl> urls;
    
    public User() {
    }
    
    public User(String signature) {
        this.signature = signature;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getSignature() {
        return signature;
    }
    
    public void setSignature(String signature) {
        this.signature = signature;
    }
    
    public Role getRole() {
        return role;
    }
    
    public void setRole(Role role) {
        this.role = role;
    }
    
    public State getState() {
        return state;
    }
    
    public void setState(State state) {
        this.state = state;
    }
    
    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public List<ShortUrl> getUrls() {
        return urls;
    }
    
    public void setUrls(List<ShortUrl> urls) {
        this.urls = urls;
    }
}
