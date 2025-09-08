package co.com.crediya.webclient.user.adapters;

import co.com.crediya.model.user.User;
import co.com.crediya.model.user.exceptions.UserNotFoundException;
import co.com.crediya.model.user.gateways.UserService;
import co.com.crediya.webclient.user.constants.UserEndpoint;
import co.com.crediya.webclient.exceptions.WebClientException;
import co.com.crediya.webclient.user.constants.UserWebClientLog;
import co.com.crediya.webclient.user.dtos.UserExistsResponseDto;
import co.com.crediya.webclient.user.dtos.UserResponseDto;
import co.com.crediya.webclient.user.mappers.UserResponseMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class UserServiceWebClientAdapter implements UserService {

    private final WebClient userWebClient;
    private final UserResponseMapper userResponseMapper;

    public UserServiceWebClientAdapter(
            @Qualifier("userWebClient") WebClient userWebClient,
            UserResponseMapper userResponseMapper
    ) {
        this.userWebClient = userWebClient;
        this.userResponseMapper = userResponseMapper;
    }
    @Override
    public Mono<Boolean> existsByIdentityNumber(String identityNumber) {
        log.info(UserWebClientLog. EXISTS_BY_IDENTITY_NUMBER_REQUEST_START.getMessage(), identityNumber);

        return userWebClient
                .get()
                .uri(UserEndpoint.VALIDATE_USER_EXISTENCE_BY_IDENTITY_NUMBER.getEndpoint(), identityNumber)
                .retrieve()
                .bodyToMono(UserExistsResponseDto.class)
                .map(response -> {
                    log.info(UserWebClientLog. EXISTS_BY_IDENTITY_NUMBER_REQUEST_SUCCESS.getMessage(),
                            response.exists(), identityNumber);
                    return response.exists();
                })
                .onErrorResume(error -> {
                    log.error(UserWebClientLog. EXISTS_BY_IDENTITY_NUMBER_REQUEST_ERROR.getMessage(),
                            identityNumber, error.getMessage(), error);
                    return Mono.error(new WebClientException());
                });
    }

    @Override
    public Mono<User> getUserByIdentityNumber(String identityNumber) {
        log.info(UserWebClientLog.GET_BY_IDENTITY_NUMBER_REQUEST_START.getMessage(), identityNumber);

        return userWebClient
                .get()
                .uri(UserEndpoint.GET_BY_IDENTITY_NUMBER.getEndpoint(), identityNumber)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> {
                    log.warn(UserWebClientLog.GET_BY_IDENTITY_NUMBER_REQUEST_NOT_FOUND.getMessage(), identityNumber);
                    return Mono.error(new UserNotFoundException());
                })
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> Mono.error(new WebClientException()))
                .bodyToMono(UserResponseDto.class)
                .map(userResponseMapper::toDomain)
                .doOnSuccess(user -> log.info(UserWebClientLog.GET_BY_IDENTITY_NUMBER_REQUEST_SUCCESS.getMessage(),
                        user.identityNumber(), identityNumber))
                .doOnError(error -> log.error(UserWebClientLog.GET_BY_IDENTITY_NUMBER_REQUEST_ERROR.getMessage(),
                        identityNumber, error.getMessage(), error));
    }
}