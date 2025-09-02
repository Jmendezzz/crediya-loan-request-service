package co.com.crediya.api.rest.loanapplication.config;


import co.com.crediya.api.dto.PaginatedResponseDto;
import co.com.crediya.api.exception.ErrorResponse;
import co.com.crediya.api.rest.loanapplication.constant.LoanApplicationEndpoint;
import co.com.crediya.api.rest.loanapplication.constant.LoanApplicationQueryParam;
import co.com.crediya.api.rest.loanapplication.dto.CreateLoanApplicationRequestDto;
import co.com.crediya.api.rest.loanapplication.dto.LoanApplicationResponseDto;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import org.springdoc.core.fn.builders.operation.Builder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import java.util.function.Consumer;
import static org.springdoc.core.fn.builders.apiresponse.Builder.responseBuilder;
import static org.springdoc.core.fn.builders.content.Builder.contentBuilder;
import static org.springdoc.core.fn.builders.parameter.Builder.parameterBuilder;
import static org.springdoc.core.fn.builders.requestbody.Builder.requestBodyBuilder;
import static org.springdoc.core.fn.builders.schema.Builder.schemaBuilder;

public class LoanApplicationApiConfig {

    public static Consumer<Builder> createApplicantDocsConsumer() {
        return builder -> builder
                .summary(LoanApplicationEndpoint.CREATE_LOAN_APPLICATION.getSummary())
                .description(LoanApplicationEndpoint.CREATE_LOAN_APPLICATION.getDescription())
                .operationId(LoanApplicationEndpoint.CREATE_LOAN_APPLICATION.getOperationId())
                .requestBody(requestBodyBuilder()
                        .required(true)
                        .content(contentBuilder()
                                .mediaType(MediaType.APPLICATION_JSON_VALUE)
                                .schema(schemaBuilder()
                                        .implementation(CreateLoanApplicationRequestDto.class)
                                )
                        )
                )
                .response(responseBuilder()
                        .responseCode(HttpStatus.CREATED.name())
                        .content(contentBuilder()
                                .mediaType(MediaType.APPLICATION_JSON_VALUE)
                                .schema(schemaBuilder()
                                        .implementation(LoanApplicationResponseDto.class)
                                )
                        )
                )
                .response(responseBuilder()
                        .responseCode(HttpStatus.BAD_REQUEST.name())
                        .content(contentBuilder()
                                .mediaType(MediaType.APPLICATION_JSON_VALUE)
                                .schema(schemaBuilder()
                                        .implementation(ErrorResponse.class)
                                )
                        )
                );
    }

    public static Consumer<Builder> getLoanApplicationsDocsConsumer() {
        return builder -> builder
                .summary(LoanApplicationEndpoint.GET_LOAN_APPLICATIONS.getSummary())
                .description(LoanApplicationEndpoint.GET_LOAN_APPLICATIONS.getDescription())
                .operationId(LoanApplicationEndpoint.GET_LOAN_APPLICATIONS.getOperationId())
                .parameter(parameterBuilder()
                        .name(LoanApplicationQueryParam.CUSTOMER_IDENTITY_NUMBER.getParam())
                        .description(LoanApplicationQueryParam.CUSTOMER_IDENTITY_NUMBER.getDescription())
                        .required(false)
                        .schema(schemaBuilder().implementation(String.class))
                )
                .parameter(parameterBuilder()
                        .name(LoanApplicationQueryParam.AMOUNT.getParam())
                        .description(LoanApplicationQueryParam.AMOUNT.getDescription())
                        .required(false)
                        .schema(schemaBuilder().implementation(Double.class))
                )
                .parameter(parameterBuilder()
                        .name(LoanApplicationQueryParam.TERM_IN_MONTHS.getParam())
                        .description(LoanApplicationQueryParam.TERM_IN_MONTHS.getDescription())
                        .required(false)
                        .schema(schemaBuilder().implementation(Integer.class))
                )
                .parameter(parameterBuilder()
                        .name(LoanApplicationQueryParam.STATE_ID.getParam())
                        .description(LoanApplicationQueryParam.STATE_ID.getDescription())
                        .required(false)
                        .schema(schemaBuilder().implementation(Long.class))
                )
                .parameter(parameterBuilder()
                        .name(LoanApplicationQueryParam.TYPE_ID.getParam())
                        .description(LoanApplicationQueryParam.TYPE_ID.getDescription())
                        .required(false)
                        .schema(schemaBuilder().implementation(Long.class))
                )
                .parameter(parameterBuilder()
                        .name(LoanApplicationQueryParam.PAGE.getParam())
                        .description(LoanApplicationQueryParam.PAGE.getDescription())
                        .required(false)
                        .schema(schemaBuilder().implementation(Integer.class))
                )
                .parameter(parameterBuilder()
                        .name(LoanApplicationQueryParam.SIZE.getParam())
                        .description(LoanApplicationQueryParam.SIZE.getDescription())
                        .required(false)
                        .schema(schemaBuilder().implementation(Integer.class))
                )
                .response(responseBuilder()
                        .responseCode(HttpStatus.OK.name())
                        .description("Successful retrieval of loan applications")
                        .content(contentBuilder()
                                .mediaType(MediaType.APPLICATION_JSON_VALUE)
                                .schema(schemaBuilder()
                                        .implementation(PaginatedResponseDto.class)
                                )
                        )
                )
                .response(responseBuilder()
                        .responseCode(HttpStatus.BAD_REQUEST.name())
                        .description("Invalid request parameters")
                        .content(contentBuilder()
                                .mediaType(MediaType.APPLICATION_JSON_VALUE)
                                .schema(schemaBuilder()
                                        .implementation(ErrorResponse.class)
                                )
                        )
                );
    }

}
