package co.com.crediya.model.loanapplication;
import co.com.crediya.model.loanapplicationstate.LoanApplicationState;
import co.com.crediya.model.loanapplicationtype.LoanApplicationType;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class LoanApplication {
    private Long id;
    private String customerIdentityNumber;
    private Double amount;
    private Integer termInMonths;
    private LoanApplicationType type;
    private LoanApplicationState state;
}
