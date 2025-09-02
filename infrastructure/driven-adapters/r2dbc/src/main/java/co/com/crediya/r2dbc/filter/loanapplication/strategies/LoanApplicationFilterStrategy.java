package co.com.crediya.r2dbc.filter.loanapplication.strategies;

import co.com.crediya.model.loanapplication.LoanApplicationQuery;
import co.com.crediya.r2dbc.filter.CriteriaBuilder;

public interface LoanApplicationFilterStrategy {
    void apply(LoanApplicationQuery query, CriteriaBuilder builder);
}