package co.com.crediya.api.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ActuatorEndpoint {
    HEALTH("/actuator/health"),
    INFO("/actuator/info");
    private final String path;
}
