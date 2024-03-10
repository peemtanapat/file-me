package com.peemtanapat.fileme.fileservice.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.Serializable;

import static com.peemtanapat.fileme.fileservice.util.JWTUtil.cleanBearer;

@Slf4j
@Service
public class JWTService implements Serializable {

    private static final long serialVersionUID = -2550185165626007488L;

    @Value("${my.jwt.secret}")
    private String jwtSecret;

    public Jws<Claims> verifyToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(jwtSecret.getBytes())
                    .parseClaimsJws(token);

            log.debug("Token verification successful");
            return claims;
        } catch (Exception e) {
            log.error("Token verification failed: " + e.getMessage());
            return null;
        }
    }

    public String getUsernameFromToken(String token) {
        Jws<Claims> claims = verifyToken(cleanBearer(token));
        if (claims != null) {
            return (String) claims.getBody().get("username");
        }
        return null;
    }
}
