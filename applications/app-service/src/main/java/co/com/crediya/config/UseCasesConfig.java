package co.com.crediya.config;

import co.com.crediya.model.loanapplication.gateways.LoanApplicationRepository;
import co.com.crediya.model.loanapplication.gateways.UserService;
import co.com.crediya.model.loanapplicationstate.gateways.LoanApplicationStateRepository;
import co.com.crediya.model.loanapplicationtype.gateways.LoanApplicationTypeRepository;
import co.com.crediya.usecase.loanapplication.LoanApplicationUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(basePackages = "co.com.crediya.usecase",
        includeFilters = {
                @ComponentScan.Filter(type = FilterType.REGEX, pattern = "^.+UseCase$")
        },
        useDefaultFilters = false)
public class UseCasesConfig {

        @Bean
        public LoanApplicationUseCase loanApplicationUseCase(
                LoanApplicationRepository loanApplicationRepository,
                LoanApplicationTypeRepository loanApplicationTypeRepository,
                LoanApplicationStateRepository loanApplicationStateRepository,
                UserService userService
                ){
                return new LoanApplicationUseCase(
                        loanApplicationRepository,
                        loanApplicationTypeRepository,
                        loanApplicationStateRepository,
                        userService
                );
        }
}
