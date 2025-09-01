package co.com.crediya.model.auth.gateways;

import java.util.List;

public interface TokenService {
    boolean validateToken(String token);
    String extractUsername(String token);
    Long extractUserId(String token);
    List<String> extractRoles(String token);
}