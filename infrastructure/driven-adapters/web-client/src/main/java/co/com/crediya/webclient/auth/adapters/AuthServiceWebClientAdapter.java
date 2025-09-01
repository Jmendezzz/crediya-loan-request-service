package co.com.crediya.webclient.auth.adapters;

import co.com.crediya.model.auth.RoleAuth;
import co.com.crediya.model.auth.UserAuth;
import co.com.crediya.model.auth.gateways.AuthService;
import co.com.crediya.webclient.auth.constants.AuthEndpoint;
import co.com.crediya.webclient.auth.constants.AuthWebClientLog;
import co.com.crediya.webclient.auth.dtos.AuthMeResponseDto;
import co.com.crediya.webclient.exceptions.WebClientException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class AuthServiceWebClientAdapter implements AuthService {

    private final WebClient.Builder webClientBuilder;
    private final String authServiceBaseUrl;

    public AuthServiceWebClientAdapter(
            WebClient.Builder webClientBuilder,
            @Value("${services.user.base-url}") String authServiceBaseUrl
    ) {
        this.webClientBuilder = webClientBuilder;
        this.authServiceBaseUrl = authServiceBaseUrl;
    }

    @Override
    public Mono<UserAuth> getCurrentUser() {
        log.info(AuthWebClientLog.REQUEST_START.getMessage());

        return webClientBuilder
                .baseUrl(authServiceBaseUrl)
                .build()
                .get()
                .uri(AuthEndpoint.ME.getEndpoint())
                .retrieve()
                .bodyToMono(AuthMeResponseDto.class)
                .map(response -> {
                    log.info(AuthWebClientLog.REQUEST_SUCCESS.getMessage(),
                            response.id(), response.email());

                    return new UserAuth(
                            response.id(),
                            response.email(),
                            response.identityNumber(),
                            new RoleAuth(
                                    response.role().id(),
                                    response.role().name()
                            )
                    );
                })
                .onErrorResume(error -> {
                    log.error(AuthWebClientLog.REQUEST_ERROR.getMessage(), error.getMessage(), error);
                    return Mono.error(new WebClientException());
                });
    }
}