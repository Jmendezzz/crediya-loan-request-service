package co.com.crediya.model.loanrequesttype;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class LoanRequestType {
    private Long id;
    private String name;
    private String description;
    private Long minAmount;
    private Long maxAmount;
    private Double interestRate;
    private Boolean autoValidation;
}
