package co.com.crediya.sqs.sender.loanapplication.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LoanApplicationSQSQueueName {
    LOAN_APPLICATION_STATE_CHANGED("loanApplicationStateChanged");
    private final String key;
}
