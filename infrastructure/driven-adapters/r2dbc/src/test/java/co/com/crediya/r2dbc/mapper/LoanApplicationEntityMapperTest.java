package co.com.crediya.r2dbc.mapper;

import co.com.crediya.model.loanapplication.LoanApplication;
import co.com.crediya.model.loanapplicationstate.LoanApplicationState;
import co.com.crediya.model.loanapplicationtype.LoanApplicationType;
import co.com.crediya.r2dbc.entity.LoanApplicationEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class LoanApplicationEntityMapperTest {

    private LoanApplicationEntityMapperImpl mapper;

    @BeforeEach
    void setUp() {
        mapper = new LoanApplicationEntityMapperImpl();
    }

    @Test
    void shouldReturnNullWhenToEntityWithNullLoanApplication() {
        LoanApplicationEntity result = mapper.toEntity(null);
        assertThat(result).isNull();
    }

    @Test
    void shouldMapToEntityWithAllFields() {
        LoanApplicationState state = LoanApplicationState.builder()
                .id(10L)
                .name("PENDING")
                .description("Pending review")
                .build();

        LoanApplicationType type = LoanApplicationType.builder()
                .id(20L)
                .name("CONSUMO")
                .build();

        LoanApplication domain = LoanApplication.builder()
                .id(1L)
                .customerIdentityNumber("12345")
                .amount(5000.0)
                .termInMonths(12)
                .state(state)
                .type(type)
                .build();

        LoanApplicationEntity result = mapper.toEntity(domain);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getCustomerIdentityNumber()).isEqualTo("12345");
        assertThat(result.getAmount()).isEqualTo(5000.0);
        assertThat(result.getTermInMonths()).isEqualTo(12);
        assertThat(result.getStateId()).isEqualTo(10L);
        assertThat(result.getTypeId()).isEqualTo(20L);
    }

    @Test
    void shouldMapToEntityWhenStateAndTypeAreNull() {
        LoanApplication domain = LoanApplication.builder()
                .id(2L)
                .customerIdentityNumber("67890")
                .amount(10000.0)
                .termInMonths(24)
                .build();

        LoanApplicationEntity result = mapper.toEntity(domain);

        assertThat(result).isNotNull();
        assertThat(result.getStateId()).isNull();
        assertThat(result.getTypeId()).isNull();
        assertThat(result.getId()).isEqualTo(2L);
        assertThat(result.getCustomerIdentityNumber()).isEqualTo("67890");
        assertThat(result.getAmount()).isEqualTo(10000.0);
        assertThat(result.getTermInMonths()).isEqualTo(24);
    }

    @Test
    void shouldReturnNullWhenToDomainWithAllNulls() {
        LoanApplication result = mapper.toDomain(null, null, null);
        assertThat(result).isNull();
    }

    @Test
    void shouldMapToDomainWithAllFields() {
        LoanApplicationEntity entity = new LoanApplicationEntity();
        entity.setId(3L);
        entity.setCustomerIdentityNumber("99999");
        entity.setAmount(2000.0);
        entity.setTermInMonths(6);

        LoanApplicationState state = LoanApplicationState.builder()
                .id(30L)
                .name("APPROVED")
                .description("Approved application")
                .build();

        LoanApplicationType type = LoanApplicationType.builder()
                .id(40L)
                .name("VEHICULO")
                .build();

        LoanApplication result = mapper.toDomain(entity, state, type);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(3L);
        assertThat(result.getCustomerIdentityNumber()).isEqualTo("99999");
        assertThat(result.getAmount()).isEqualTo(2000.0);
        assertThat(result.getTermInMonths()).isEqualTo(6);
        assertThat(result.getState()).isEqualTo(state);
        assertThat(result.getType()).isEqualTo(type);
    }

    @Test
    void shouldMapToDomainWhenStateAndTypeAreNull() {
        LoanApplicationEntity entity = new LoanApplicationEntity();
        entity.setId(4L);
        entity.setCustomerIdentityNumber("11111");
        entity.setAmount(3000.0);
        entity.setTermInMonths(18);

        LoanApplication result = mapper.toDomain(entity, null, null);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(4L);
        assertThat(result.getCustomerIdentityNumber()).isEqualTo("11111");
        assertThat(result.getAmount()).isEqualTo(3000.0);
        assertThat(result.getTermInMonths()).isEqualTo(18);
        assertThat(result.getState()).isNull();
        assertThat(result.getType()).isNull();
    }
}
