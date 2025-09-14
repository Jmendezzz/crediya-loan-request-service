package co.com.crediya.api.exception;

import co.com.crediya.model.common.exceptions.DomainException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@Order(-2)
@RequiredArgsConstructor
public class GlobalExceptionHandler implements ErrorWebExceptionHandler {

    private final ObjectMapper objectMapper;

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        ErrorResponse response;

        if (ex instanceof DomainException e) {
            response = buildResponse(HttpStatus.BAD_REQUEST,
                    Map.of("code", e.getCode(), "message", e.getMessage()));
        } else if (ex instanceof ConstraintViolationException e) {
            List<Map<String, String>> errors = e.getConstraintViolations().stream()
                    .map(v -> Map.of("field", v.getPropertyPath().toString(),
                            "message", v.getMessage()))
                    .toList();

            response = buildResponse(HttpStatus.BAD_REQUEST,
                    Map.of("code", "VALIDATION_ERROR", "errors", errors));
        } else {
            log.error("Unhandled exception", ex);

            response = buildResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                    Map.of("code", "INTERNAL_ERROR",
                            "message", "Ocurrió un error interno, por favor intente más tarde."));
        }

        exchange.getResponse().setStatusCode(response.status());
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);

        byte[] bytes;
        try {
            bytes = objectMapper.writeValueAsBytes(response.body());
        } catch (Exception e) {
            bytes = """
                    {"code":"SERIALIZATION_ERROR","message":"Error serializando respuesta"}
                    """.getBytes(StandardCharsets.UTF_8);
        }

        return exchange.getResponse()
                .writeWith(Mono.just(exchange.getResponse().bufferFactory().wrap(bytes)));
    }

    private ErrorResponse buildResponse(HttpStatus status, Map<String, Object> body) {
        return new ErrorResponse(status, body);
    }
}
