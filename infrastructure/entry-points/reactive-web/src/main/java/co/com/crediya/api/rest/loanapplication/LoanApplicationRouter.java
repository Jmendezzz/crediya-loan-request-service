package co.com.crediya.api.rest.loanapplication;

import co.com.crediya.api.dto.PageRequestDto;
import co.com.crediya.api.rest.loanapplication.config.LoanApplicationApiConfig;
import co.com.crediya.api.rest.loanapplication.constant.LoanApplicationEndpoint;
import co.com.crediya.api.rest.loanapplication.constant.LoanApplicationQueryParam;
import co.com.crediya.api.rest.loanapplication.dto.CreateLoanApplicationRequestDto;
import co.com.crediya.api.rest.loanapplication.dto.LoanApplicationFilterRequestDto;
import co.com.crediya.api.utils.QueryParamParser;
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
                .GET(LoanApplicationEndpoint.GET_LOAN_APPLICATIONS.getPath(),
                        req -> {
                            LoanApplicationFilterRequestDto filterDto = new LoanApplicationFilterRequestDto(
                                    QueryParamParser.toStringValue(req.queryParam(LoanApplicationQueryParam.CUSTOMER_IDENTITY_NUMBER.getParam())),
                                    QueryParamParser.toDouble(req.queryParam(LoanApplicationQueryParam.AMOUNT.getParam())),
                                    QueryParamParser.toInteger(req.queryParam(LoanApplicationQueryParam.TERM_IN_MONTHS.getParam())),
                                    QueryParamParser.toLong(req.queryParam(LoanApplicationQueryParam.STATE_ID.getParam())),
                                    QueryParamParser.toLong(req.queryParam(LoanApplicationQueryParam.TYPE_ID.getParam()))
                            );
                            PageRequestDto pageDto = new PageRequestDto(
                                    req.queryParam(LoanApplicationQueryParam.PAGE.getParam()).map(Integer::valueOf).orElse(0),
                                    req.queryParam(LoanApplicationQueryParam.SIZE.getParam()).map(Integer::valueOf).orElse(10)
                            );

                            return handler.getLoanApplications(filterDto, pageDto)
                                    .flatMap(dto -> ServerResponse.ok()
                                            .contentType(MediaType.APPLICATION_JSON)
                                            .bodyValue(dto));
                        },
                        ops -> LoanApplicationApiConfig.getLoanApplicationsDocsConsumer().accept(ops)
                )
                .build();
    }
}