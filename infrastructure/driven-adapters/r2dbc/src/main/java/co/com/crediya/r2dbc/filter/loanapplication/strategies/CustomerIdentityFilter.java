package co.com.crediya.r2dbc.filter.loanapplication.strategies;

import co.com.crediya.model.loanapplication.queries.LoanApplicationQuery;
import co.com.crediya.r2dbc.constants.LoanApplicationColumn;
import co.com.crediya.r2dbc.filter.CriteriaBuilder;
import co.com.crediya.r2dbc.filter.FilterOperator;

public class CustomerIdentityFilter implements LoanApplicationFilterStrategy {
    @Override
    public void apply(LoanApplicationQuery query, CriteriaBuilder builder) {
        query.customerIdentityNumber()
                .ifPresent(value -> builder.add(LoanApplicationColumn.CUSTOMER_IDENTITY_NUMBER.getColumn(), value, FilterOperator.EQUALS));
    }
}
