package co.com.crediya.r2dbc.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LoanApplicationStateColumn {
    ID("id"),
    NAME("name");
    private final String column;
}
