package org.example.computerstore.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;
import java.util.function.Function;

@Component
public class JwtUtil {

    private final String secret = "3r9iTTOMAswgv7fAObC3KltdTzmeOfw4xwBYXgWdkjU=";

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().verifyWith(getKey()).build().parseSignedClaims(token).getPayload();
    }

    public String createToken(String username) {
        return Jwts.builder()
                .subject(username)
                .signWith(getKey())
                .compact();
    }

    private SecretKey getKey() {
//        byte[] keyBytes = secret.getBytes();
//        return Keys.hmacShaKeyFor(keyBytes);
//        var tempKey = Jwts.SIG.HS256.key().build();
//        String encodedKey = Base64.getEncoder().encodeToString(tempKey.getEncoded());
//        System.out.println("KEY");
//        System.out.println(encodedKey);
//        return tempKey;

        byte[] decodedKey = Base64.getDecoder().decode(secret);
        SecretKey key = Keys.hmacShaKeyFor(decodedKey);
        return key;
    }

    public boolean validateToken(String token, String username) {
//        try {
//            Jwts.parser().verifyWith(getKey()).build().parseSignedClaims(token);
//
//            //OK, we can trust this JWT
//            return true;
//
//        } catch (JwtException e) {
//            //don't trust the JWT!
//            return false;
//        }

        final String extractedUsername = extractUsername(token);
        return extractedUsername.equals(username);
    }
}
