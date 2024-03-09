package com.peemtanapat.fileme.fileservice.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;

import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.function.Function;

public class JWTUtil implements Serializable {

    private static final long serialVersionUID = -2550185165626007488L;

    @Value("${jwt.secret}")
    private String jwtSecret = "besogoodtheycantignoreyou";

    public Jws<Claims> verifyToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(jwtSecret.getBytes())
                    .parseClaimsJws(token);

            System.out.println("Token verification successful");
            return claims;
        } catch (Exception e) {
            System.out.println("Token verification failed: " + e.getMessage());
            return null;
        }
    }

    private final String cleanBearer(String originalToken) {
        return originalToken.replace("Bearer ", "");
    }

    public String getUsernameFromToken(String token) {
        Jws<Claims> claims = verifyToken(cleanBearer(token));
        if (claims != null) {
            return (String) claims.getBody().get("username");
        }
        return null;
    }
}
