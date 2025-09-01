package co.com.crediya.jwt.adapters;

import co.com.crediya.jwt.constants.JwtClaim;
import co.com.crediya.model.auth.gateways.TokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Collection;
import java.util.List;


@Component
public class JwtTokenServiceAdapter implements TokenService {

    private final String secret;


    public JwtTokenServiceAdapter(
            @Value("${security.jwt.secret}")
            String secret){
        this.secret = secret;
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
    public Long extractUserId(String token) {
        Claims claims = getClaims(token);
        Object userId = claims.get(JwtClaim.USER_ID.getClaim());

        if (userId instanceof Integer intVal) {
            return intVal.longValue();
        }
        if (userId instanceof Long longVal) {
            return longVal;
        }
        if (userId instanceof String strVal) {
            return Long.parseLong(strVal);
        }

        throw new IllegalArgumentException();
    }

    @Override
    public List<String> extractRoles(String token) {
        Claims claims =  getClaims(token);

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

    private Claims getClaims(String token){
        return Jwts.parser()
                .verifyWith(getSigningKey(secret))
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

}
