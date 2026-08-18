package com.ram.Authentication.Authorization.utils;

import com.ram.Authentication.Authorization.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JwtUtils {

    public static String generateJwtToken(User user){
        return Jwts.builder().subject(user.getUserName())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 8 * 60 * 60 * 1000))
                .claim("roles", user.getUserRole())
                .claim("email verified", user.getIsEnabled())
                .signWith(SignatureAlgorithm.HS256, "RamAuthenticationAuthorizationRamAuthenticationAuthorizationRamAuthenticationAuthorization")
                .compact();
    }

    public static Claims validateToken(String token) {
        Claims claim = Jwts.parser().setSigningKey("RamAuthenticationAuthorizationRamAuthenticationAuthorizationRamAuthenticationAuthorization")
                .build().parseSignedClaims(token).getPayload();

        return claim;
    }
}
