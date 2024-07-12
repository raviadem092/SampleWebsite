package com.recordent.entity;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.NonNull;

@Entity
@Table(name="user")
public class User implements Serializable {

    public User() {
        super();
    }

    public User(Long id, @NonNull String name, @NonNull String email, @NonNull String mobile, @NonNull String password) {
        super();
        this.id = id;
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.password = password;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String name;

    @NonNull
    private String email;

    @NonNull
    private String mobile;

    @NonNull
    private String password;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        System.out.println("Email========== " + email);
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        System.out.println("Mobile========== " + mobile);
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", name=" + name + ", email=" + email + ", mobile=" + mobile + ", password=" + password + "]";
    }

    public boolean checkPassword(String rawPassword) {
        return this.password.equals(rawPassword);
    }
}
