package com.irctc.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtServiceUtil {

    @Value("${secret.key}") private String SECRET_KEY;

    private static final int JWT_TOKEN_VALIDATION = 5 * 60 * 60;

    public String getUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date getExpireDate(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private boolean isTokenExpire(String token) {
        final Date expirationDate = getExpireDate(token);
        return expirationDate.before(new Date());
    }

    public String generateToken(String subject) {
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDATION * 1000))
                .signWith(getSignKey())
                .compact();
    }

    public boolean validateToken(String token, String subject) {
        final String username = getUsername(token);
        return (username.equals(subject) && !isTokenExpire(token));
    }

    private Key getSignKey() {
        byte[] byteKey = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(byteKey);
    }
}
