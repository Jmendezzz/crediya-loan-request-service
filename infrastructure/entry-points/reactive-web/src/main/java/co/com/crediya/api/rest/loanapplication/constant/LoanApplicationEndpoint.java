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
    ),
    GET_LOAN_APPLICATIONS(
            "",
            "getLoanApplications",
            "Obtener y filtrar solicitudes de prestamo",
            "Obtener y filtrar solicitudes de prestamo por parte de un admin / asesor"
    ),
    UPDATE_LOAN_APPLICATION_STATE(
            "/{id}/state",
            "updateLoanApplicationState",
            "Actualizar estado de solicitud",
            "Permite a un asesor aprobar o rechazar una solicitud"
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

