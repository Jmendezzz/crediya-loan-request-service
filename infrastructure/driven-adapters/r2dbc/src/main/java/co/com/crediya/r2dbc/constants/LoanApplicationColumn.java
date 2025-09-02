package co.com.crediya.r2dbc.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LoanApplicationColumn {
    ID("id"),
    CUSTOMER_IDENTITY_NUMBER("customer_identity_number"),
    AMOUNT("amount"),
    TERM_IN_MONTHS("term_in_months"),
    STATE_ID("state_id"),
    TYPE_ID("type_id");

    private final String column;
}