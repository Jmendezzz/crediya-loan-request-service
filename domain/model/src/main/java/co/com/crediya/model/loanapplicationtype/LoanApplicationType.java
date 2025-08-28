package co.com.crediya.model.loanapplicationtype;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class LoanApplicationType {
    private Long id;
    private String name;
    private String description;
    private Long minAmount;
    private Long maxAmount;
    private Double interestRate;
    private Boolean autoValidation;
}
