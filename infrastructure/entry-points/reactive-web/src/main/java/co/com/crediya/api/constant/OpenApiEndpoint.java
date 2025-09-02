package co.com.crediya.api.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OpenApiEndpoint {
    API_DOCS("/v3/api-docs/**"),
    SWAGGER_UI("/swagger-ui.html"),
    SWAGGER_UI_RESOURCES("/swagger-ui/**"),
    WEB_JARS("/webjars/**");

    private final String path;
}
