package co.com.crediya.sqs.sender.loanapplication.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LoanApplicationSQSQueueName {
    LOAN_APPLICATION_STATE_CHANGED("loanApplicationStateChanged"),
    LOAN_APPLICATION_AUTO_VALIDATION_REQUESTED("loanApplicationAutoValidationRequested"),
    LOAN_APPLICATION_APPROVED("loanApplicationApproved");
    private final String key;
}
