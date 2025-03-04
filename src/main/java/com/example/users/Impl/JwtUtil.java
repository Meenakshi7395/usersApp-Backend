package com.example.users.Impl;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.security.Signature;
import java.util.Date;

import static javax.crypto.Cipher.SECRET_KEY;
@Service
public class JwtUtil {
    private static final String SECRET_KEY = "my-secret-key-my-secret-key-my-secret-key-my-secret-key-my-secret-key-my-secret-key-my-secret-key";
    private static final long EXPIRATION_TIME = 1000 * 60 * 60; // 10 days
    private Key getSignKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    }

    public JwtUtil()
    {

    }
    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .subject(userDetails.getUserName())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSignKey())
                .compact();
    }

    public String extractUsername(String token) {
        return Jwts.parser()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token) {
        try{
            Jwts.parser()
                    .setSigningKey(getSignKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }
}