package co.com.crediya.api.rest.loanapplication.dto;

import co.com.crediya.api.rest.user.constant.UserOpenApiSchema;
import co.com.crediya.api.rest.user.constant.UserValidationMessage;
import co.com.crediya.model.user.constants.UserConstant;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record CreateApplicantRequestDto(

        @NotBlank(message = UserValidationMessage.FIRST_NAME_REQUIRED)
        @Schema(description = UserOpenApiSchema.FIRST_NAME_DESCRIPTION, example = UserOpenApiSchema.FIRST_NAME_EXAMPLE)
        String firstName,

        @NotBlank(message = UserValidationMessage.LAST_NAME_REQUIRED)
        @Schema(description = UserOpenApiSchema.LAST_NAME_DESCRIPTION, example = UserOpenApiSchema.LAST_NAME_EXAMPLE)
        String lastName,

        @NotBlank(message = UserValidationMessage.IDENTITY_NUMBER_REQUIRED)
        @Schema(description = UserOpenApiSchema.IDENTITY_NUMBER_DESCRIPTION, example = UserOpenApiSchema.IDENTITY_NUMBER_EXAMPLE)
        String identityNumber,

        @Schema(description = UserOpenApiSchema.PHONE_NUMBER_DESCRIPTION, example = UserOpenApiSchema.PHONE_NUMBER_EXAMPLE)
        String phoneNumber,

        @Past(message = UserValidationMessage.BIRTHDATE_INVALID)
        @Schema(description = UserOpenApiSchema.BIRTHDATE_DESCRIPTION, example = UserOpenApiSchema.BIRTHDATE_EXAMPLE)
        LocalDate birthdate,

        @Schema(description = UserOpenApiSchema.ADDRESS_DESCRIPTION, example = UserOpenApiSchema.ADDRESS_EXAMPLE)
        String address,

        @NotNull(message = UserValidationMessage.BASE_SALARY_REQUIRED)
        @Min(value = UserConstant.MIN_BASE_SALARY, message = UserValidationMessage.BASE_SALARY_RANGE)
        @Max(value = UserConstant.MAX_BASE_SALARY, message = UserValidationMessage.BASE_SALARY_RANGE)
        @Schema(description = UserOpenApiSchema.BASE_SALARY_DESCRIPTION, example = UserOpenApiSchema.BASE_SALARY_EXAMPLE)
        Long baseSalary,

        @NotBlank(message = UserValidationMessage.EMAIL_REQUIRED)
        @Email(message = UserValidationMessage.EMAIL_INVALID)
        @Schema(description = UserOpenApiSchema.EMAIL_DESCRIPTION, example = UserOpenApiSchema.EMAIL_EXAMPLE)
        String email
) {}