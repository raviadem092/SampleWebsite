package com.recordent.service;

import java.util.HashMap;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.recordent.entity.User;
import com.recordent.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SessionService sessionService;

    public User registerUser(User user) {
        if (userRepository.findByEmail(user.getEmail()) != null || userRepository.findByMobile(user.getMobile()) != null) {
            throw new RuntimeException("User already exists with this email or mobile");
        }
        return userRepository.save(user);
    }

    public HashMap<String, Object> loginUser(String identifier, String password) {
        User user = userRepository.findByEmail(identifier);
        
        System.out.println("Service User: ============: " + user);

        if (user == null) {
            user = userRepository.findByMobile(identifier);
        }

        if (user != null && user.getPassword().equals(password)) {
            HashMap<String, Object> sessionData = new HashMap<>();
            String token = sessionService.createSession(user);
            sessionData.put("token", token);
            sessionData.put("user", user);
            return sessionData;
        } else {
            throw new RuntimeException("Invalid email/mobile or password");
        }
    }

    
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }
    
    public User validateSession(String token) {
        return sessionService.validateSession(token);
    }

    
}