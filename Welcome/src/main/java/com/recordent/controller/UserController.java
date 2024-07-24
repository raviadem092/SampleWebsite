package com.recordent.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.recordent.entity.User;
import com.recordent.service.UserService;

@RestController
@CrossOrigin
@RequestMapping("/recordent")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public User signUp(@RequestBody User user) {
        System.out.println("User Details Controller SignUp =====: " + user.toString());
        return userService.registerUser(user);
    }

    @PostMapping("/signin")
    public HashMap<String, Object> signIn(@RequestBody SignInRequest signInRequest) {
        
        return userService.loginUser(signInRequest.getEmailOrMobile(), signInRequest.getPassword());
    }
}

class SignInRequest {
    private String emailOrMobile;
    private String password;

    public String getEmailOrMobile() {
        return emailOrMobile;
    }

    public void setEmailOrMobile(String emailOrMobile) {
        this.emailOrMobile = emailOrMobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}