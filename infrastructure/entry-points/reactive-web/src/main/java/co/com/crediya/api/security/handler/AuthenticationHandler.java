package co.com.crediya.api.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component
public class AuthenticationHandler implements ServerAuthenticationEntryPoint {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public Mono<Void> commence(ServerWebExchange exchange, AuthenticationException ex) {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> body = Map.of(
                "code", HttpStatus.UNAUTHORIZED.name(),
                "message", "Error en la autenticación"
        );

        return writeResponse(exchange, body);
    }

    private Mono<Void> writeResponse(ServerWebExchange exchange, Map<String, Object> body) {
        try {
            byte[] bytes = mapper.writeValueAsBytes(body);
            DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
            return exchange.getResponse().writeWith(Mono.just(buffer));
        } catch (Exception e) {
            return Mono.error(e);
        }
    }
}