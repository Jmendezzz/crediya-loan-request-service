package co.com.crediya.model.auth.gateways;

import reactor.core.publisher.Mono;

public interface AuthContext {

    Mono<Long> getCurrentUserId();
}
