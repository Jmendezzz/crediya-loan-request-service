package co.com.crediya.r2dbc.mapper;

import co.com.crediya.model.loanapplicationstate.LoanApplicationState;
import co.com.crediya.r2dbc.entity.LoanApplicationStateEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class LoanApplicationStateEntityMapperTest {
    private LoanApplicationStateEntityMapperImpl mapper;

    @BeforeEach
    void setUp() {
        mapper = new LoanApplicationStateEntityMapperImpl();
    }

    @Test
    void shouldReturnNullWhenToDomainWithNullEntity() {
        LoanApplicationState result = mapper.toDomain(null);
        assertThat(result).isNull();
    }

    @Test
    void shouldMapToDomainWithAllFields() {
        LoanApplicationStateEntity entity = new LoanApplicationStateEntity();
        entity.setId(1L);
        entity.setName("PENDING_REVIEW");
        entity.setDescription("Solicitud en espera de revisión");

        LoanApplicationState result = mapper.toDomain(entity);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("PENDING_REVIEW");
        assertThat(result.getDescription()).isEqualTo("Solicitud en espera de revisión");
    }

    @Test
    void shouldReturnNullWhenToEntityWithNullDomain() {
        LoanApplicationStateEntity result = mapper.toEntity(null);
        assertThat(result).isNull();
    }

    @Test
    void shouldMapToEntityWithAllFields() {
        LoanApplicationState domain = LoanApplicationState.builder()
                .id(2L)
                .name("APPROVED")
                .description("Solicitud aprobada")
                .build();

        LoanApplicationStateEntity result = mapper.toEntity(domain);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(2L);
        assertThat(result.getName()).isEqualTo("APPROVED");
        assertThat(result.getDescription()).isEqualTo("Solicitud aprobada");
    }
}
