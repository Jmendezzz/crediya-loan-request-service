package co.com.crediya.r2dbc.filter.loanapplication;

import co.com.crediya.r2dbc.filter.loanapplication.strategies.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LoanApplicationFilterType {
    CUSTOMER_IDENTITY(new CustomerIdentityFilter()),
    AMOUNT(new AmountFilter()),
    TERM(new TermFilter()),
    STATE_ID(new StateIdFilter()),
    TYPE_ID(new TypeIdFilter());

    private final LoanApplicationFilterStrategy strategy;
}