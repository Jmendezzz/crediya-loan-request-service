package co.com.crediya.api.exception;

import co.com.crediya.model.common.exceptions.DomainException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
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
import java.util.Optional;
import java.util.function.Function;

@Component
@Order(-2)
@RequiredArgsConstructor
public class GlobalExceptionHandler implements ErrorWebExceptionHandler {

    private final ObjectMapper objectMapper;

    private final Map<Class<? extends Throwable>, Function<Throwable, ErrorResponse>> handlers =
            Map.of(
                    DomainException.class, ex -> {
                        DomainException e = (DomainException) ex;
                        return new ErrorResponse(HttpStatus.BAD_REQUEST,
                                Map.of("code", e.getCode(), "message", e.getMessage()));
                    },
                    ConstraintViolationException.class, ex -> {
                        ConstraintViolationException e = (ConstraintViolationException) ex;
                        List<Map<String, String>> errors = e.getConstraintViolations().stream()
                                .map(v -> Map.of("field", v.getPropertyPath().toString(),
                                        "message", v.getMessage()))
                                .toList();

                        return new ErrorResponse(HttpStatus.BAD_REQUEST,
                                Map.of("code", "VALIDATION_ERROR", "errors", errors));
                    });

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        ErrorResponse response = handlers.entrySet().stream()
                .filter(entry -> entry.getKey().isAssignableFrom(ex.getClass()))
                .findFirst()
                .map(entry -> entry.getValue().apply(ex))
                .orElseGet(() -> new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                        Map.of("code", "INTERNAL_ERROR",
                                "message", Optional.ofNullable(ex.getMessage())
                                        .orElse("Unexpected error"))));

        exchange.getResponse().setStatusCode(response.status());
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);

        byte[] bytes;
        try {
            bytes = objectMapper.writeValueAsBytes(response.body());
        } catch (Exception e) {
            bytes = """
                    {"code":"SERIALIZATION_ERROR","message":"Error serializing response"}
                    """.getBytes(StandardCharsets.UTF_8);
        }

        return exchange.getResponse().writeWith(
                Mono.just(exchange.getResponse().bufferFactory().wrap(bytes))
        );
    }
}