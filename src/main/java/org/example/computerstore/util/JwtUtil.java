package org.example.computerstore.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.function.Function;

@Component
@Order(Ordered.LOWEST_PRECEDENCE - 100)
public class JwtUtil {

    private final String secret = "3r9iTTOMAswgv7fAObC3KltdTzmeOfw4xwBYXgWdkjU=";

    // private static final long TOKEN_EXPIRATION_TIME = 24 * 60 * 60 * 1000;
    private static final long TEST_TOKEN_EXPIRATION_TIME = 5 * 60 * 1000;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
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
//                .expiration(new Date(System.currentTimeMillis() + TOKEN_EXPIRATION_TIME))
                .expiration(new Date(System.currentTimeMillis() + TEST_TOKEN_EXPIRATION_TIME))
                .compact();
    }

    private SecretKey getKey() {
        byte[] decodedKey = Base64.getDecoder().decode(secret);
        SecretKey key = Keys.hmacShaKeyFor(decodedKey);
        return key;
    }

    public boolean validateToken(String token, String username) {
        final String extractedUsername = extractUsername(token);
        final Date extractedExpiration = extractExpiration(token);
        if (extractedUsername == null || extractedExpiration == null) {
            return false;
        }
        if (extractedExpiration.before(new Date())) {
            return false;
        }
        return extractedUsername.equals(username);
    }
}
