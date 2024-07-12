package com.recordent.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.recordent.entity.User;
import com.recordent.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User registerUser(User user) {
        if (userRepository.findByEmail(user.getEmail()) != null || userRepository.findByMobile(user.getMobile()) != null) {
            System.out.println("Email====DB====== " + userRepository.findByEmail(user.getEmail()));
            System.out.println("Mobile: ======DB======: " + userRepository.findByMobile(user.getMobile()));
            throw new RuntimeException("User already exists with this email or mobile");
        }
        return userRepository.save(user);
    }

    public User loginUser(String identifier, String password) {
        User user = userRepository.findByEmail(identifier);
        System.out.println("Service User: ============: " + user);

        if (user == null) {
            user = userRepository.findByMobile(identifier);
            System.out.println("Service User IF: ============: " + user);
            System.out.println("Service User: =======REPO=====: " + userRepository.findByMobile(identifier));
        }

        if (user != null && user.getPassword().equals(password)) {
            System.out.println("Service User Password: ============: " + user.getPassword() + " : " + password);
            return user;
        } else {
            throw new RuntimeException("Invalid email/mobile or password");
        }
    }
}
