package co.com.crediya.api.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ApiResource {
    USERS("/users");

    private final String resource;
}
