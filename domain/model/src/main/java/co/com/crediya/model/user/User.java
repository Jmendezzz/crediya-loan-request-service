package co.com.crediya.model.user;

import lombok.Builder;

@Builder
public record User(
        String identityNumber,
        String email,
        String firstName,
        String lastName,
        Long baseSalary
) { }