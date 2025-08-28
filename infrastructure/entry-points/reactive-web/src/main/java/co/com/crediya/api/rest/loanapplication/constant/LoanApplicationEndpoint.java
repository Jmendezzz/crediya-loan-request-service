package co.com.crediya.api.rest.loanapplication.constant;

import co.com.crediya.api.constant.ApiResource;
import co.com.crediya.api.constant.ApiVersion;
import lombok.Getter;

@Getter
public enum LoanApplicationEndpoint {
    CREATE_LOAN_APPLICATION(
             "/create",
            "createLoanApplication",
            "Crear solicitud de prestamo",
            "Registra una nueva solicitud de prestamo en el sistema"
    );

    private final String path;
    private final String operationId;
    private final String summary;
    private final String description;

    LoanApplicationEndpoint(String path, String operationId, String summary, String description) {
        this.path = ApiVersion.V1.getVersion() + ApiResource.LOAN_APPLICATIONS.getResource() + path;
        this.operationId = operationId;
        this.summary = summary;
        this.description = description;
    }
}

