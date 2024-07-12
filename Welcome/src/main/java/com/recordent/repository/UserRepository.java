package com.recordent.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.recordent.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    User findByMobile(String mobile);
}
