package co.com.crediya.r2dbc.filter.loanapplication;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.EnumSet;
import java.util.Set;

@Getter
@RequiredArgsConstructor
public enum LoanApplicationQueryScenario {

    GENERAL(EnumSet.allOf(LoanApplicationFilterType.class));

    private final Set<LoanApplicationFilterType> filters;
}