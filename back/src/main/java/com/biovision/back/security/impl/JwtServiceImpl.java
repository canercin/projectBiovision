package com.biovision.back.security.impl;

import com.biovision.back.security.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtServiceImpl implements JwtService {
    // todo get field value from properties files
    private static final Long EXPIRATION_TIME = Integer.toUnsignedLong(1000 * 60 * 60 * 10); // 10 hours

    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(this.getSignKey())
                .compact();
    }

    public String extractUsername(String token) {
        return this.extractClaim(token, Claims::getSubject);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = this.extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public boolean isTokenExpired(String token) {
        return this.extractClaim(token, Claims::getExpiration).before(new Date());
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(this.getSignKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey getSignKey(){
        String SECRET = "dfec5a2c81d842d7c9757c37996b7a5ed6c1bee7c590803c8accf995b8986e12a411613d102b1af99a6a57a43b9a81671c6aae0c2fb7ca509f6c916836ad2c591179cb466818c8b8adc53c11bea6c0fc93a449d56b7d7f4349b2ac2f0664914672b3bb2c08a7c8eb358436304630404d3165b2d174550d71f9589716365a654e7f3d437c7bfe7ead61734981394865d8c9dc84a28146b2c73f08f4275c5dbe80de6bd44696b02c1c521147030d2b70ba01c1c044b8a74e83e62c5963a6d26b0dc88d7dff2762f7eafed1c2258388ee21958a668ae04c3d98fd499c5a867958e531663f3a9b77aac4ae129b361775275a3cd69199cddae116aca7d574e8e6941e";
        byte[] keyBytes = SECRET.getBytes();
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
