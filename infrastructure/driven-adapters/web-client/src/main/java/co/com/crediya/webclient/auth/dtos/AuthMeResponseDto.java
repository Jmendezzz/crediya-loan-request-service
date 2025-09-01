package co.com.crediya.webclient.auth.dtos;

public record AuthMeResponseDto(
        Long id,
        String firstName,
        String lastName,
        String identityNumber,
        String phoneNumber,
        String birthdate,
        String address,
        Double baseSalary,
        String email,
        RoleResponseDto role
) {
}
