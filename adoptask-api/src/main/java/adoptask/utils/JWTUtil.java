package adoptask.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JWTUtil {

    private final SecretKey SECRET_KEY = Keys.hmacShaKeyFor("n5E@J+*L2qoW3rT$K7H!D6mF8gB#Z9P0cX1vA_yU-iVlSjGhO=MpR4tQ%fxYdCzN&b".getBytes());

    public String getId(String token) {
    	
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
        
        return claims.getSubject();
    }

    public String generateToken(String subject) {
    	
        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 2))
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
                .compact();
    }
}
