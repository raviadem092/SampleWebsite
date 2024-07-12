package com.recordent.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.recordent.entity.User;
import com.recordent.service.UserService;

@RestController
@CrossOrigin
@RequestMapping
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public User signUp(@RequestBody User user) {
        System.out.println("User Details Controller SignUp =====: " + user.toString());
        return userService.registerUser(user);
    }

    @PostMapping("/signin")
    public User signIn(@RequestBody SignInRequest signInRequest) {
        System.out.println("User Details Controller SignIn =====: " + signInRequest.getEmailOrMobile() + " ::: " + signInRequest.getPassword());
        System.out.println("Controller Response: =====: " + userService.loginUser(signInRequest.getEmailOrMobile(), signInRequest.getPassword()));
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
