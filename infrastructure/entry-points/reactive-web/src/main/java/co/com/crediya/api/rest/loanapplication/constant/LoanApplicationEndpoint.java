package co.com.crediya.api.rest.user.constant;

import co.com.crediya.api.constant.ApiResource;
import co.com.crediya.api.constant.ApiVersion;
import lombok.Getter;

@Getter
public enum UserEndpoint {
    CREATE_APPLICANT(
             "/applicants",
            "createApplicant",
            "Crear solicitante",
            "Registra un nuevo solicitante en el sistema"
    );

    private final String path;
    private final String operationId;
    private final String summary;
    private final String description;

    UserEndpoint(String path, String operationId, String summary, String description) {
        this.path = ApiVersion.V1.getPrefix() + ApiResource.USERS.getResource() + path;
        this.operationId = operationId;
        this.summary = summary;
        this.description = description;
    }
}

