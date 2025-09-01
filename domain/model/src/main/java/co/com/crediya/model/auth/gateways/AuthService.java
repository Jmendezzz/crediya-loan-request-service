package co.com.crediya.model.auth.gateways;

import co.com.crediya.model.auth.UserAuth;
import reactor.core.publisher.Mono;

public interface AuthService {
    Mono<UserAuth> getCurrentUser();
}
