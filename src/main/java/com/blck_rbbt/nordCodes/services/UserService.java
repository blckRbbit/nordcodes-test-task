package com.blck_rbbt.nordCodes.services;

import com.blck_rbbt.nordCodes.entities.User;
import com.blck_rbbt.nordCodes.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;
    
    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }
    
    public Optional<User> findUserBySignature(String signature) {
        return userRepository.findBySignature(signature);
    }
    
    public void saveUser(String signature) {
        userRepository.save(new User(signature));
    }
    
    public void saveUser(User user) {
        userRepository.save(user);
    }
    
    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    

    
}
