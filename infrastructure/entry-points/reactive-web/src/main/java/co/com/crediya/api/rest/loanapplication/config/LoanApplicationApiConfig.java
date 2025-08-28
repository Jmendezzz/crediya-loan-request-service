package co.com.crediya.api.rest.loanapplication.config;


import co.com.crediya.api.exception.ErrorResponse;
import co.com.crediya.api.rest.loanapplication.constant.LoanApplicationEndpoint;
import co.com.crediya.api.rest.loanapplication.dto.CreateLoanApplicationRequestDto;
import co.com.crediya.api.rest.loanapplication.dto.LoanApplicationResponseDto;
import org.springdoc.core.fn.builders.operation.Builder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import java.util.function.Consumer;
import static org.springdoc.core.fn.builders.apiresponse.Builder.responseBuilder;
import static org.springdoc.core.fn.builders.content.Builder.contentBuilder;
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
}
