package co.com.crediya.webclient.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder()
                .filter((request, next) ->
                        ReactiveSecurityContextHolder.getContext()
                                .map(SecurityContext::getAuthentication)
                                .flatMap(auth -> {
                                    Object credentials = auth.getCredentials();
                                    if (credentials instanceof String token) {
                                        ClientRequest newRequest = ClientRequest.from(request)
                                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                                                .build();
                                        return next.exchange(newRequest);
                                    }
                                    return next.exchange(request);
                                })
                );
    }
}