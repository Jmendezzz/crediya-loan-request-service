package co.com.crediya.model.loanapplication.gateways;

import reactor.core.publisher.Mono;

public interface UserService {
    Mono<Boolean> existsByIdentityNumber(String identityNumber);
}
