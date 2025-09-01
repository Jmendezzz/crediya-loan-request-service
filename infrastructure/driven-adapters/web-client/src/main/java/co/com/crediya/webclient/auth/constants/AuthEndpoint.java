package co.com.crediya.webclient.auth.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum AuthEndpoint {
    ME("api/v1/auth/me");

    private final String endpoint;
}
