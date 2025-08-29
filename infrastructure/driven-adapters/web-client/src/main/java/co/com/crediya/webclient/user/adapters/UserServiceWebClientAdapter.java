package co.com.crediya.webclient.user.adapters;

import co.com.crediya.model.loanapplication.gateways.UserService;
import co.com.crediya.webclient.user.constants.UserEndpoint;
import co.com.crediya.webclient.exceptions.WebClientException;
import co.com.crediya.webclient.user.constants.UserWebClientLog;
import co.com.crediya.webclient.user.dtos.UserExistsResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class UserServiceWebClientAdapter implements UserService {

    private final WebClient.Builder webClientBuilder;
    private final String userServiceBaseUrl;

    public UserServiceWebClientAdapter(
            WebClient.Builder webClientBuilder,
            @Value("${services.user.base-url}") String userServiceBaseUrl
    ) {
        this.webClientBuilder = webClientBuilder;
        this.userServiceBaseUrl = userServiceBaseUrl;
    }

    @Override
    public Mono<Boolean> existsByIdentityNumber(String identityNumber) {
        log.info(UserWebClientLog.REQUEST_START.getMessage(), identityNumber);

        return webClientBuilder
                .baseUrl(userServiceBaseUrl)
                .build()
                .get()
                .uri(UserEndpoint.VALIDATE_USER_EXISTENCE_BY_IDENTITY_NUMBER.getEndpoint(), identityNumber)
                .retrieve()
                .bodyToMono(UserExistsResponseDto.class)
                .map(response -> {
                    log.info(UserWebClientLog.REQUEST_SUCCESS.getMessage(),
                            response.exists(), identityNumber);
                    return response.exists();
                })
                .onErrorResume(error -> {
                    log.error(UserWebClientLog.REQUEST_ERROR.getMessage(),
                            identityNumber, error.getMessage(), error);
                    return Mono.error(new WebClientException());
                });
    }
}