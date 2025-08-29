package co.com.crediya.r2dbc.mapper;

import co.com.crediya.model.loanapplicationtype.LoanApplicationType;
import co.com.crediya.r2dbc.entity.LoanApplicationTypeEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-29T08:54:38-0500",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.7 (Amazon.com Inc.)"
)
@Component
public class LoanApplicationTypeEntityMapperImpl implements LoanApplicationTypeEntityMapper {

    @Override
    public LoanApplicationType toDomain(LoanApplicationTypeEntity loanApplicationTypeEntity) {
        if ( loanApplicationTypeEntity == null ) {
            return null;
        }

        LoanApplicationType.LoanApplicationTypeBuilder loanApplicationType = LoanApplicationType.builder();

        loanApplicationType.id( loanApplicationTypeEntity.getId() );
        loanApplicationType.name( loanApplicationTypeEntity.getName() );
        loanApplicationType.description( loanApplicationTypeEntity.getDescription() );
        loanApplicationType.minAmount( loanApplicationTypeEntity.getMinAmount() );
        loanApplicationType.maxAmount( loanApplicationTypeEntity.getMaxAmount() );
        loanApplicationType.interestRate( loanApplicationTypeEntity.getInterestRate() );
        loanApplicationType.autoValidation( loanApplicationTypeEntity.getAutoValidation() );

        return loanApplicationType.build();
    }

    @Override
    public LoanApplicationTypeEntity toEntity(LoanApplicationType loanApplicationType) {
        if ( loanApplicationType == null ) {
            return null;
        }

        LoanApplicationTypeEntity loanApplicationTypeEntity = new LoanApplicationTypeEntity();

        loanApplicationTypeEntity.setId( loanApplicationType.getId() );
        loanApplicationTypeEntity.setName( loanApplicationType.getName() );
        loanApplicationTypeEntity.setDescription( loanApplicationType.getDescription() );
        loanApplicationTypeEntity.setMinAmount( loanApplicationType.getMinAmount() );
        loanApplicationTypeEntity.setMaxAmount( loanApplicationType.getMaxAmount() );
        loanApplicationTypeEntity.setInterestRate( loanApplicationType.getInterestRate() );
        loanApplicationTypeEntity.setAutoValidation( loanApplicationType.getAutoValidation() );

        return loanApplicationTypeEntity;
    }
}
