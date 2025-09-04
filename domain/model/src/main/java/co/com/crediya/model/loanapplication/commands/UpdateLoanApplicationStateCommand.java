package co.com.crediya.model.loanapplication.commands;

public record UpdateLoanApplicationStateCommand(
        Long loanApplicationId,
        Long stateId
) { }