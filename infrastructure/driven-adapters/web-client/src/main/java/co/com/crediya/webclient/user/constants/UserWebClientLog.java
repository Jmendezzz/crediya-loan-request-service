package co.com.crediya.webclient.user.constants;

import lombok.Getter;

@Getter
public enum UserWebClientLog {

    REQUEST_START("Calling UserService to validate existence of identityNumber={}"),
    REQUEST_SUCCESS("UserService response: exists={} for identityNumber={}"),
    REQUEST_ERROR("Error calling UserService for identityNumber={} - {}");

    private final String message;

    UserWebClientLog(String message) {
        this.message = message;
    }
}