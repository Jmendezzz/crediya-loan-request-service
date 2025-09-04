package co.com.crediya.sqs.sender.loanapplication;

import co.com.crediya.model.loanapplication.events.LoanApplicationStateChanged;
import co.com.crediya.model.loanapplication.gateways.LoanApplicationEventPublisher;
import co.com.crediya.sqs.sender.SQSSender;
import co.com.crediya.sqs.sender.config.SQSSenderProperties;
import co.com.crediya.sqs.sender.loanapplication.constants.LoanApplicationSQSQueueName;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class LoanApplicationEventPublisherSQSAdapter implements LoanApplicationEventPublisher {

    private final SQSSender publisher;
    private final SQSSenderProperties properties;

    @Override
    public Mono<Void> publish(LoanApplicationStateChanged event) {
        return publisher.publish(
                event,
                properties.queues().get(LoanApplicationSQSQueueName.LOAN_APPLICATION_STATE_CHANGED.getKey())
                ).then();
    }
}