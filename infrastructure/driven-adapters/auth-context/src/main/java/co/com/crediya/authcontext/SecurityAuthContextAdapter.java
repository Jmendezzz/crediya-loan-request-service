package co.com.crediya.authcontext;

import co.com.crediya.model.auth.gateways.AuthContext;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class SecurityAuthContextAdapter implements AuthContext {
    @Override
    public Mono<Long> getCurrentUserId() {
        return ReactiveSecurityContextHolder.getContext()
                .map(SecurityContext::getAuthentication)
                .map(auth -> (Long) auth.getPrincipal());
        }
}
