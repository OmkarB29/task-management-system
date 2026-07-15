package com.omkar.taskmanager.security;

import com.omkar.taskmanager.user.User;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetails;
import javax.crypto.SecretKey;
import java.util.Date;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetails;


@Service
public class JwtService {
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private long expiration;

    private SecretKey secretKey;
    @PostConstruct
    public void init(){
        secretKey= Keys.hmacShaKeyFor(secret.getBytes());
    }
    public String generateToken(User user){
        return Jwts.builder()
                .subject(user.getEmail())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(secretKey)
                .compact();
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        return extractUsername(token).equals(userDetails.getUsername());
    }

}
