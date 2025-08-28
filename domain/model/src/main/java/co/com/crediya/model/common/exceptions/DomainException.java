package co.com.crediya.model.common.exceptions;

import lombok.Getter;

@Getter
public class DomainException extends RuntimeException {
    private final String message;
    private final String code;

    public DomainException(String message, String code){
        super(message);
        this.message = message;
        this.code = code;
    }
}
