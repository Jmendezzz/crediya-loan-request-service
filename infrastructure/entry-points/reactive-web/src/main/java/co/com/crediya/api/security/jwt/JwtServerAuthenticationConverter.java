package co.com.crediya.api.security.jwt;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public class JwtServerAuthenticationConverter implements ServerAuthenticationConverter {

    private static final String BEARER_PREFIX = "Bearer ";

    @Override
    public Mono<Authentication> convert(ServerWebExchange exchange) {
        return extractToken(exchange)
                .map(this::createAuthenticationToken);
    }

    private Mono<String> extractToken(ServerWebExchange exchange) {
        return Mono.justOrEmpty(exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION))
                .filter(this::hasBearerPrefix)
                .map(this::removeBearerPrefix);
    }

    private boolean hasBearerPrefix(String header) {
        return header.startsWith(BEARER_PREFIX);
    }

    private String removeBearerPrefix(String header) {
        return header.substring(BEARER_PREFIX.length());
    }

    private Authentication createAuthenticationToken(String token) {
        return new UsernamePasswordAuthenticationToken(null, token);
    }
}