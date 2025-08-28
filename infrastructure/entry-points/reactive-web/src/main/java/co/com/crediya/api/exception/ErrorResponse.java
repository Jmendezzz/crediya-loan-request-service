package co.com.crediya.api.exception;

import org.springframework.http.HttpStatus;

public record ErrorResponse(HttpStatus status, Object body) {
}
