package co.com.crediya.webclient.auth.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AuthWebClientLog {
    REQUEST_START("Calling AuthService /auth/me"),
    REQUEST_SUCCESS("AuthService responded with userId={} email={}"),
    REQUEST_ERROR("Error calling AuthService /auth/me: {}");

    private final String message;
}