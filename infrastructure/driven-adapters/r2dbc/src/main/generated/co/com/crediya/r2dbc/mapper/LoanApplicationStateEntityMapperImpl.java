package co.com.crediya.r2dbc.mapper;

import co.com.crediya.model.loanapplicationstate.LoanApplicationState;
import co.com.crediya.r2dbc.entity.LoanApplicationStateEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-29T08:54:37-0500",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.7 (Amazon.com Inc.)"
)
@Component
public class LoanApplicationStateEntityMapperImpl implements LoanApplicationStateEntityMapper {

    @Override
    public LoanApplicationState toDomain(LoanApplicationStateEntity loanApplicationStateEntity) {
        if ( loanApplicationStateEntity == null ) {
            return null;
        }

        LoanApplicationState.LoanApplicationStateBuilder loanApplicationState = LoanApplicationState.builder();

        loanApplicationState.id( loanApplicationStateEntity.getId() );
        loanApplicationState.name( loanApplicationStateEntity.getName() );
        loanApplicationState.description( loanApplicationStateEntity.getDescription() );

        return loanApplicationState.build();
    }

    @Override
    public LoanApplicationStateEntity toEntity(LoanApplicationState loanApplicationState) {
        if ( loanApplicationState == null ) {
            return null;
        }

        LoanApplicationStateEntity loanApplicationStateEntity = new LoanApplicationStateEntity();

        loanApplicationStateEntity.setId( loanApplicationState.getId() );
        loanApplicationStateEntity.setName( loanApplicationState.getName() );
        loanApplicationStateEntity.setDescription( loanApplicationState.getDescription() );

        return loanApplicationStateEntity;
    }
}
