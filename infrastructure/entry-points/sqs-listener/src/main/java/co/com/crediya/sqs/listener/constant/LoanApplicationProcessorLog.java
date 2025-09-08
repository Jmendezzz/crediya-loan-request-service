package co.com.crediya.sqs.listener.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LoanApplicationProcessorLog {

    RECEIVED_MESSAGE("Received message from SQS. body={}"),
    PARSE_SUCCESS("Successfully parsed LoanApplicationAutoValidationCompleted event. loanApplicationId={}, decision={}"),
    PARSE_ERROR("Error parsing event body. body={} error={}"),
    PROCESS_START("Starting to apply auto-validation decision for loanApplicationId={}"),
    PROCESS_SUCCESS("Successfully applied auto-validation decision for loanApplicationId={}"),
    PROCESS_ERROR("Error applying auto-validation decision for loanApplicationId={}, error={}");

    private final String message;
}
