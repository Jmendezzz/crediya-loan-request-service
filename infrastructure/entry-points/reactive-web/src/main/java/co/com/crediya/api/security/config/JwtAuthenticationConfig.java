package co.com.crediya.api.security.config;

import co.com.crediya.api.security.jwt.JwtReactiveAuthenticationManager;
import co.com.crediya.api.security.jwt.JwtServerAuthenticationConverter;
import co.com.crediya.model.auth.gateways.TokenService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;

@Configuration
public class JwtAuthenticationConfig {

    @Bean
    public ReactiveAuthenticationManager jwtAuthenticationManager(TokenService tokenService) {
        return new JwtReactiveAuthenticationManager(tokenService);
    }

    @Bean
    public ServerAuthenticationConverter jwtServerAuthenticationConverter() {
        return new JwtServerAuthenticationConverter();
    }

    @Bean
    public AuthenticationWebFilter jwtAuthenticationWebFilter(
            ReactiveAuthenticationManager jwtAuthenticationManager,
            ServerAuthenticationConverter jwtServerAuthenticationConverter
    ) {
        AuthenticationWebFilter filter = new AuthenticationWebFilter(jwtAuthenticationManager);
        filter.setServerAuthenticationConverter(jwtServerAuthenticationConverter);
        return filter;
    }
}
