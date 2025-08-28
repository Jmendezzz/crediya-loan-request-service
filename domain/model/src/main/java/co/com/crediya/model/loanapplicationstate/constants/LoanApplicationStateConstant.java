package co.com.crediya.model.loanapplicationstate.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LoanApplicationStateConstant {

    PENDING_REVIEW("PENDIENTE_REVISION", "Solicitud en espera de revisión"),
    MANUAL_REVIEW("REVISION_MANUAL", "Solicitud requiere revisión manual"),
    APPROVED("APROBADA", "Solicitud aprobada"),
    REJECTED("RECHAZADA", "Solicitud rechazada"),
    CANCELED("CANCELADA", "Solicitud cancelada por el cliente o el sistema");

    private final String name;
    private final String description;
}

