package co.com.crediya.jwt.adapters;

import co.com.crediya.jwt.constants.JwtClaim;
import co.com.crediya.model.auth.gateways.TokenService;
import co.com.crediya.model.user.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Component
public class JwtTokenServiceAdapter implements TokenService {

    private final String secret;

    private final long expiration;

    public JwtTokenServiceAdapter(
            @Value("${security.jwt.secret}")
            String secret,
            @Value("${security.jwt.expiration-ms}")
            long expiration
    ){
        this.expiration = expiration;
        this.secret = secret;
    }

    @Override
    public String generateToken(User user) {
        return Jwts.builder()
                .subject(user.getEmail())
                .claim(JwtClaim.ROLE.getClaim(), user.getRole().getName())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigningKey(secret))
                .compact();
    }

    @Override
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(getSigningKey(secret))
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getSubject();
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    @Override
    public String extractUsername(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey(secret))
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    @Override
    public List<String> extractRoles(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(getSigningKey(secret))
                .build()
                .parseSignedClaims(token)
                .getPayload();

        Object roleClaim = claims.get(JwtClaim.ROLE.getClaim());
        if (roleClaim instanceof String role) {
            return List.of(role);
        }
        if (roleClaim instanceof Collection<?> roles) {
            return roles.stream().map(Object::toString).toList();
        }
        return List.of();
    }

    private SecretKey getSigningKey(String secret) {
        byte[] secretBytes = Decoders.BASE64URL.decode(secret);
        return Keys.hmacShaKeyFor(secretBytes);
    }
}
