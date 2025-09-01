package co.com.crediya.model.auth;

public record UserAuth(
        Long id,
        String email,
        String identityNumber,
        RoleAuth role
) {
}
