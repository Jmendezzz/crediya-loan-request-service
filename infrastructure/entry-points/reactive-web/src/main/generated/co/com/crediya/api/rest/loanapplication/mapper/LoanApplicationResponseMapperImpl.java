package co.com.crediya.api.rest.loanapplication.mapper;

import co.com.crediya.api.rest.loanapplication.dto.LoanApplicationResponseDto;
import co.com.crediya.model.loanapplication.LoanApplication;
import co.com.crediya.model.loanapplicationstate.LoanApplicationState;
import co.com.crediya.model.loanapplicationtype.LoanApplicationType;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-29T08:54:39-0500",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.7 (Amazon.com Inc.)"
)
@Component
public class LoanApplicationResponseMapperImpl implements LoanApplicationResponseMapper {

    @Override
    public LoanApplicationResponseDto toDto(LoanApplication loanApplication) {
        if ( loanApplication == null ) {
            return null;
        }

        Long id = null;
        String customerIdentityNumber = null;
        Double amount = null;
        Integer termInMonths = null;
        LoanApplicationType type = null;
        LoanApplicationState state = null;

        id = loanApplication.getId();
        customerIdentityNumber = loanApplication.getCustomerIdentityNumber();
        amount = loanApplication.getAmount();
        termInMonths = loanApplication.getTermInMonths();
        type = loanApplication.getType();
        state = loanApplication.getState();

        LoanApplicationResponseDto loanApplicationResponseDto = new LoanApplicationResponseDto( id, customerIdentityNumber, amount, termInMonths, type, state );

        return loanApplicationResponseDto;
    }
}
