package co.com.crediya.r2dbc.filter.loanapplication.strategies;

import co.com.crediya.model.loanapplication.LoanApplicationQuery;
import co.com.crediya.r2dbc.constants.LoanApplicationColumn;
import co.com.crediya.r2dbc.filter.CriteriaBuilder;
import co.com.crediya.r2dbc.filter.FilterOperator;

public class StateIdFilter implements LoanApplicationFilterStrategy {
    @Override
    public void apply(LoanApplicationQuery query, CriteriaBuilder builder) {
        query.loanApplicationStateId()
                .ifPresent(value -> builder.add(LoanApplicationColumn.STATE_ID.getColumn(), value, FilterOperator.EQUALS));
    }
}