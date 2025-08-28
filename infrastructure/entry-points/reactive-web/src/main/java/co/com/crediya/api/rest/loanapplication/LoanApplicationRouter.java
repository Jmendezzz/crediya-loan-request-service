package co.com.crediya.api.rest.loanapplication;

import co.com.crediya.api.rest.user.config.UserOpenApiConfig;
import co.com.crediya.api.rest.user.constant.UserEndpoint;
import co.com.crediya.api.rest.user.dto.CreateApplicantRequestDto;
import org.springdoc.webflux.core.fn.SpringdocRouteBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;


@Configuration
public class UserRoute {

    @Bean
    public RouterFunction<ServerResponse> userRoutes(UserHandler handler) {
        return SpringdocRouteBuilder.route()
                .POST(UserEndpoint.CREATE_APPLICANT.getPath(),
                        req -> req.bodyToMono(CreateApplicantRequestDto.class)
                                .flatMap(handler::createApplicant)
                                .flatMap(dto ->
                                        ServerResponse
                                            .status(HttpStatus.CREATED)
                                            .contentType(MediaType.APPLICATION_JSON)
                                            .bodyValue(dto)
                                ),
                        ops -> UserOpenApiConfig.createApplicantDocsConsumer().accept(ops)
                )
                .build();
    }
}