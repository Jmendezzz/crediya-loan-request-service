package co.com.crediya.r2dbc.mapper;

import co.com.crediya.model.loanapplication.LoanApplication;
import co.com.crediya.model.loanapplicationstate.LoanApplicationState;
import co.com.crediya.model.loanapplicationtype.LoanApplicationType;
import co.com.crediya.r2dbc.entity.LoanApplicationEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-29T08:54:38-0500",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.7 (Amazon.com Inc.)"
)
@Component
public class LoanApplicationEntityMapperImpl implements LoanApplicationEntityMapper {

    @Override
    public LoanApplicationEntity toEntity(LoanApplication loanApplication) {
        if ( loanApplication == null ) {
            return null;
        }

        LoanApplicationEntity loanApplicationEntity = new LoanApplicationEntity();

        loanApplicationEntity.setStateId( loanApplicationStateId( loanApplication ) );
        loanApplicationEntity.setTypeId( loanApplicationTypeId( loanApplication ) );
        loanApplicationEntity.setId( loanApplication.getId() );
        loanApplicationEntity.setCustomerIdentityNumber( loanApplication.getCustomerIdentityNumber() );
        loanApplicationEntity.setAmount( loanApplication.getAmount() );
        loanApplicationEntity.setTermInMonths( loanApplication.getTermInMonths() );

        return loanApplicationEntity;
    }

    @Override
    public LoanApplication toDomain(LoanApplicationEntity loanApplication, LoanApplicationState state, LoanApplicationType type) {
        if ( loanApplication == null && state == null && type == null ) {
            return null;
        }

        LoanApplication.LoanApplicationBuilder loanApplication1 = LoanApplication.builder();

        if ( loanApplication != null ) {
            loanApplication1.id( loanApplication.getId() );
            loanApplication1.customerIdentityNumber( loanApplication.getCustomerIdentityNumber() );
            loanApplication1.amount( loanApplication.getAmount() );
            loanApplication1.termInMonths( loanApplication.getTermInMonths() );
        }
        loanApplication1.state( state );
        loanApplication1.type( type );

        return loanApplication1.build();
    }

    private Long loanApplicationStateId(LoanApplication loanApplication) {
        LoanApplicationState state = loanApplication.getState();
        if ( state == null ) {
            return null;
        }
        return state.getId();
    }

    private Long loanApplicationTypeId(LoanApplication loanApplication) {
        LoanApplicationType type = loanApplication.getType();
        if ( type == null ) {
            return null;
        }
        return type.getId();
    }
}
