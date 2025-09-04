package co.com.crediya.webclient.user.constants;

import lombok.Getter;

@Getter
public enum UserWebClientLog {

    EXISTS_BY_IDENTITY_NUMBER_REQUEST_START("Calling UserService to validate existence of identityNumber={}"),
    EXISTS_BY_IDENTITY_NUMBER_REQUEST_SUCCESS("UserService response: exists={} for identityNumber={}"),
    EXISTS_BY_IDENTITY_NUMBER_REQUEST_ERROR("Error calling UserService for identityNumber={} - {}"),

    GET_BY_IDENTITY_NUMBER_REQUEST_START("Calling UserService with identityNumber={}"),
    GET_BY_IDENTITY_NUMBER_REQUEST_NOT_FOUND("User not found for identityNumber={}"),
    GET_BY_IDENTITY_NUMBER_REQUEST_SUCCESS("UserService response success for identityNumber={}"),
    GET_BY_IDENTITY_NUMBER_REQUEST_ERROR("Error calling UserService for identityNumber={} - {}");


    private final String message;

    UserWebClientLog(String message) {
        this.message = message;
    }
}