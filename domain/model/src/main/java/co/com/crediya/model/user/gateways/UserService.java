package co.com.crediya.model.user.gateways;

import co.com.crediya.model.user.User;
import reactor.core.publisher.Mono;

public interface UserService {
    Mono<Boolean> existsByIdentityNumber(String identityNumber);
    Mono<User> getUserByIdentityNumber(String identityNumber);
}
