package co.com.crediya.config.mdc;

import io.micrometer.context.ContextRegistry;
import jakarta.annotation.PostConstruct;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

@Component
public class CorrelationIdContextAccessor {

    private static final String CORRELATION_ID_KEY = "correlationId";

    @PostConstruct
    public void registerAccessor() {
        ContextRegistry.getInstance().registerThreadLocalAccessor(
                CORRELATION_ID_KEY,
                () -> MDC.get(CORRELATION_ID_KEY),
                value -> MDC.put(CORRELATION_ID_KEY, value),
                () -> MDC.remove(CORRELATION_ID_KEY)
        );
    }
}