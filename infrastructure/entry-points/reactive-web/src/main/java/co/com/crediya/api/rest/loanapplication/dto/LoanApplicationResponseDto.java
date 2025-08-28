package co.com.crediya.api.rest.loanapplication.dto;

import co.com.crediya.api.rest.user.constant.UserOpenApiSchema;
import co.com.crediya.model.role.Role;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

public record UserResponseDto (

        @Schema(description = UserOpenApiSchema.ID_DESCRIPTION)
        Long id,

        @Schema(description = UserOpenApiSchema.FIRST_NAME_DESCRIPTION, example = UserOpenApiSchema.FIRST_NAME_EXAMPLE)
        String firstName,

        @Schema(description = UserOpenApiSchema.LAST_NAME_DESCRIPTION, example = UserOpenApiSchema.LAST_NAME_EXAMPLE)
        String lastName,

        @Schema(description = UserOpenApiSchema.IDENTITY_NUMBER_DESCRIPTION, example = UserOpenApiSchema.IDENTITY_NUMBER_EXAMPLE)
        String identityNumber,

        @Schema(description = UserOpenApiSchema.PHONE_NUMBER_DESCRIPTION, example = UserOpenApiSchema.PHONE_NUMBER_EXAMPLE)
        String phoneNumber,

        @Schema(description = UserOpenApiSchema.BIRTHDATE_DESCRIPTION, example = UserOpenApiSchema.BIRTHDATE_EXAMPLE)
        LocalDate birthdate,

        @Schema(description = UserOpenApiSchema.ADDRESS_DESCRIPTION, example = UserOpenApiSchema.ADDRESS_EXAMPLE)
        String address,

        @Schema(description = UserOpenApiSchema.BASE_SALARY_DESCRIPTION, example = UserOpenApiSchema.BASE_SALARY_EXAMPLE)
        Long baseSalary,

        @Schema(description = UserOpenApiSchema.EMAIL_DESCRIPTION, example = UserOpenApiSchema.EMAIL_EXAMPLE)
        String email,

        Role role
) {}