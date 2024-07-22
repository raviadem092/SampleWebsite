package com.recordent.service;

import com.recordent.entity.SessionToken;
import com.recordent.entity.User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class SessionService {
    private Map<String, SessionToken> sessionStore = new HashMap<>();
    private static final int SESSION_TIMEOUT_MINUTES = 10;

    public String createSession(User user) {
        String token = UUID.randomUUID().toString();
        SessionToken sessionToken = new SessionToken();
        sessionToken.setToken(token);
        sessionToken.setUserId(user.getId());
        sessionToken.setExpirationTime(LocalDateTime.now().plusMinutes(SESSION_TIMEOUT_MINUTES));

        sessionStore.put(token, sessionToken);
        return token;
    }

    public User validateSession(String token) {
        SessionToken sessionToken = sessionStore.get(token);
        System.out.println("Session Token:==>>> "+sessionToken.toString());
        System.out.println("TokeN===> "+token);
        if (sessionToken == null || sessionToken.getExpirationTime().isBefore(LocalDateTime.now())) {
            return null;
        }
        sessionToken.setExpirationTime(LocalDateTime.now().plusMinutes(SESSION_TIMEOUT_MINUTES));
        return new User(); 
    }

}
