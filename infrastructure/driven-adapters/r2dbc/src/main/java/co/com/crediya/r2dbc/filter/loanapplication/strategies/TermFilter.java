package co.com.crediya.r2dbc.filter.loanapplication.strategies;


import co.com.crediya.model.loanapplication.LoanApplicationQuery;
import co.com.crediya.r2dbc.constants.LoanApplicationColumn;
import co.com.crediya.r2dbc.filter.CriteriaBuilder;
import co.com.crediya.r2dbc.filter.FilterOperator;

public class TermFilter implements LoanApplicationFilterStrategy {
    @Override
    public void apply(LoanApplicationQuery query, CriteriaBuilder builder) {
        query.termInMonths()
                .ifPresent(value -> builder.add(LoanApplicationColumn.TERM_IN_MONTHS.getColumn(), value, FilterOperator.GREATER_THAN_OR_EQUALS));
    }
}
