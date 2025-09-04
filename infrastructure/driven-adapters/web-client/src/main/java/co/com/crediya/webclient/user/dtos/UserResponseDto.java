package co.com.crediya.webclient.user.dtos;

import java.time.LocalDate;

public record UserResponseDto(
        Long id,
        String firstName,
        String lastName,
        String identityNumber,
        String phoneNumber,
        LocalDate birthdate,
        String address,
        Double baseSalary,
        String email,
        RoleResponseDto role
        ) {
}
