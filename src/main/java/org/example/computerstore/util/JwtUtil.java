package org.example.computerstore.util;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;

@Component
public class JwtUtil {

    private final String secret = "funnelcake";

    private String createToken(String username) {
        return Jwts.builder()
                .subject(username)
                .signWith(getKey())
                .compact();
    }

    private SecretKey getKey() {
        byte[] keyBytes = secret.getBytes();
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith(getKey()).build().parseSignedClaims(token);

            //OK, we can trust this JWT
            return true;

        } catch (JwtException e) {
            //don't trust the JWT!
            return false;
        }
    }
}
