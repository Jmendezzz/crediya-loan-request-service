package co.com.crediya.api.rest.loanapplication.mapper;

import co.com.crediya.api.rest.loanapplication.dto.CreateLoanApplicationRequestDto;
import co.com.crediya.model.loanapplication.LoanApplication;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-29T08:54:39-0500",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.7 (Amazon.com Inc.)"
)
@Component
public class LoanApplicationRequestMapperImpl implements LoanApplicationRequestMapper {

    @Override
    public LoanApplication toDomain(CreateLoanApplicationRequestDto dto) {
        if ( dto == null ) {
            return null;
        }

        LoanApplication.LoanApplicationBuilder loanApplication = LoanApplication.builder();

        loanApplication.type( mapTypeIdToType( dto.typeId() ) );
        loanApplication.state( mapStateIdToState( dto.stateId() ) );
        loanApplication.customerIdentityNumber( dto.customerIdentityNumber() );
        loanApplication.amount( dto.amount() );
        loanApplication.termInMonths( dto.termInMonths() );

        return loanApplication.build();
    }
}
