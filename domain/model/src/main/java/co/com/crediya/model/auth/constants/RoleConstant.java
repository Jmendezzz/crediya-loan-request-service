package co.com.crediya.model.auth.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RoleConstant {
    ADMINISTRATOR("ADMINISTRADOR"),
    APPLICANT("SOLICITANTE");

    private final String name;
}
