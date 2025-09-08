package co.com.crediya.model.loanapplication.gateways;

import co.com.crediya.model.loanapplication.events.LoanApplicationAutoValidationRequested;
import co.com.crediya.model.loanapplication.events.LoanApplicationStateChanged;
import reactor.core.publisher.Mono;

public interface LoanApplicationEventPublisher {
    Mono<Void> publish(LoanApplicationStateChanged event);
    Mono<Void> publish(LoanApplicationAutoValidationRequested event);
}