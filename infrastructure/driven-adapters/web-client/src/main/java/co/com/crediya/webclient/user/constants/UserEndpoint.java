package co.com.crediya.webclient.user.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum UserEndpoint {

    VALIDATE_USER_EXISTENCE_BY_IDENTITY_NUMBER("api/v1/users/{identityNumber}/exists");

    private final String endpoint;
}