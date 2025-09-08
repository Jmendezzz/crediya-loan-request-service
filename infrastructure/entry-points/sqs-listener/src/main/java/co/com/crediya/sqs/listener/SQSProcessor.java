package co.com.crediya.sqs.listener;

import co.com.crediya.model.loanapplication.events.LoanApplicationAutoValidationCompleted;
import co.com.crediya.sqs.listener.constant.LoanApplicationProcessorLog;
import co.com.crediya.usecase.loanapplication.LoanApplicationUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.services.sqs.model.Message;

import java.util.function.Function;

@Service
@Slf4j
@RequiredArgsConstructor
public class SQSProcessor implements Function<Message, Mono<Void>> {
    private final LoanApplicationUseCase loanApplicationUseCase;
    private final ObjectMapper objectMapper;

    @Override
    public Mono<Void> apply(Message message) {
        log.info(LoanApplicationProcessorLog.RECEIVED_MESSAGE.getMessage(), message.body());

        return Mono.fromCallable(() -> parseEvent(message.body()))
                .doOnNext(event -> log.info(
                        LoanApplicationProcessorLog.PARSE_SUCCESS.getMessage(),
                        event.loanApplicationId(),
                        event.decision()
                ))
                .flatMap(event -> loanApplicationUseCase.applyAutoValidationDecision(event)
                        .doOnSubscribe(sub -> log.info(
                                LoanApplicationProcessorLog.PROCESS_START.getMessage(),
                                event.loanApplicationId()
                        ))
                        .doOnSuccess(app -> log.info(
                                LoanApplicationProcessorLog.PROCESS_SUCCESS.getMessage(),
                                event.loanApplicationId()
                        ))
                        .doOnError(err -> log.error(
                                LoanApplicationProcessorLog.PROCESS_ERROR.getMessage(),
                                event.loanApplicationId(),
                                err.getMessage(),
                                err
                        ))
                )
                .onErrorResume(e -> {
                    log.error(
                            LoanApplicationProcessorLog.PARSE_ERROR.getMessage(),
                            message.body(),
                            e.getMessage(),
                            e
                    );
                    return Mono.empty();
                })
                .then();
    }

    private LoanApplicationAutoValidationCompleted parseEvent(String body) {
        try {
            return objectMapper.readValue(body, LoanApplicationAutoValidationCompleted.class);
        } catch (Exception e) {
            throw new RuntimeException("Error parsing event: " + body, e);
        }
    }
}
