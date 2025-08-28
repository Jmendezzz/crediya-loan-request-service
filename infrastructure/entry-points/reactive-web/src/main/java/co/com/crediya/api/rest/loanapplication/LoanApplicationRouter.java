package co.com.crediya.api.rest.loanapplication;

import co.com.crediya.api.rest.loanapplication.config.LoanApplicationApiConfig;
import co.com.crediya.api.rest.loanapplication.constant.LoanApplicationEndpoint;
import co.com.crediya.api.rest.loanapplication.dto.CreateLoanApplicationRequestDto;
import org.springdoc.webflux.core.fn.SpringdocRouteBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;


@Configuration
public class LoanApplicationRouter {

    @Bean
    public RouterFunction<ServerResponse> userRoutes(LoanApplicationHandler handler) {
        return SpringdocRouteBuilder.route()
                .POST(LoanApplicationEndpoint.CREATE_LOAN_APPLICATION.getPath(),
                        req -> req.bodyToMono(CreateLoanApplicationRequestDto.class)
                                .flatMap(handler::createApplicant)
                                .flatMap(dto ->
                                        ServerResponse
                                            .status(HttpStatus.CREATED)
                                            .contentType(MediaType.APPLICATION_JSON)
                                            .bodyValue(dto)
                                ),
                        ops -> LoanApplicationApiConfig.createApplicantDocsConsumer().accept(ops)
                )
                .build();
    }
}