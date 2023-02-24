package com.gozip.jwt;

import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

import io.jsonwebtoken.*;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class JwtUtil {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";
    private static final long TOKEN_TIME = 60 * 60 * 1000L;

    @Value("${jwt.secret.key}")
    private String secretKey;
    private Key key;
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    // 초기 설정
    @PostConstruct
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }

    // JWT 발급
    public String createToken(String email) {
        Date date = new Date();
        return BEARER_PREFIX + Jwts.builder().
                setSubject(email).
                setExpiration(new Date(date.getTime() + TOKEN_TIME)).
                setIssuedAt(date).
                signWith(signatureAlgorithm, key)
                .compact();
    }

}
