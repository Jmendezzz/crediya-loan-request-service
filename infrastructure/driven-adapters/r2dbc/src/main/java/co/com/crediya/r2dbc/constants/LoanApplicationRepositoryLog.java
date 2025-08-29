package co.com.crediya.r2dbc.constants;

import lombok.Getter;

@Getter
public enum LoanApplicationRepositoryLog {

    SAVE_START("Saving LoanApplication with identityNumber={}"),
    SAVE_SUCCESS("LoanApplication saved successfully with ID={}"),
    SAVE_ERROR("Error saving LoanApplication: {}"),

    ENRICH_START("Enriching LoanApplicationEntity with relations. EntityId={}"),
    ENRICH_SUCCESS("LoanApplicationEntity {} enriched successfully"),
    ENRICH_ERROR("Error enriching LoanApplicationEntity {}: {}");

    private final String message;

    LoanApplicationRepositoryLog(String message) {
        this.message = message;
    }
}
