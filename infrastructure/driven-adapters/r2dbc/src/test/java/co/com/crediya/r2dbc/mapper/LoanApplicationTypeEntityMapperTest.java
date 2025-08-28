package co.com.crediya.r2dbc.mapper;

import co.com.crediya.model.loanapplicationtype.LoanApplicationType;
import co.com.crediya.r2dbc.entity.LoanApplicationTypeEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class LoanApplicationTypeEntityMapperTest {
    private LoanApplicationTypeEntityMapperImpl mapper;

    @BeforeEach
    void setUp() {
        mapper = new LoanApplicationTypeEntityMapperImpl();
    }

    @Test
    void shouldReturnNullWhenToDomainWithNullEntity() {
        LoanApplicationType result = mapper.toDomain(null);
        assertThat(result).isNull();
    }

    @Test
    void shouldMapToDomainWithAllFields() {
        LoanApplicationTypeEntity entity = new LoanApplicationTypeEntity();
        entity.setId(1L);
        entity.setName("CONSUMO");
        entity.setDescription("Préstamo de consumo");
        entity.setMinAmount(5000L);
        entity.setMaxAmount(20000L);
        entity.setInterestRate(0.18);
        entity.setAutoValidation(true);

        LoanApplicationType result = mapper.toDomain(entity);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("CONSUMO");
        assertThat(result.getDescription()).isEqualTo("Préstamo de consumo");
        assertThat(result.getMinAmount()).isEqualTo(5000L);
        assertThat(result.getMaxAmount()).isEqualTo(20000L);
        assertThat(result.getInterestRate()).isEqualTo(0.18);
        assertThat(result.getAutoValidation()).isTrue();
    }

    @Test
    void shouldReturnNullWhenToEntityWithNullDomain() {
        LoanApplicationTypeEntity result = mapper.toEntity(null);
        assertThat(result).isNull();
    }

    @Test
    void shouldMapToEntityWithAllFields() {
        LoanApplicationType domain = LoanApplicationType.builder()
                .id(2L)
                .name("VEHICULO")
                .description("Préstamo para vehículo")
                .minAmount(10000L)
                .maxAmount(100000L)
                .interestRate(0.15)
                .autoValidation(false)
                .build();

        LoanApplicationTypeEntity result = mapper.toEntity(domain);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(2L);
        assertThat(result.getName()).isEqualTo("VEHICULO");
        assertThat(result.getDescription()).isEqualTo("Préstamo para vehículo");
        assertThat(result.getMinAmount()).isEqualTo(10000L);
        assertThat(result.getMaxAmount()).isEqualTo(100000L);
        assertThat(result.getInterestRate()).isEqualTo(0.15);
        assertThat(result.getAutoValidation()).isFalse();
    }
}
