package co.com.crediya.model.loanapplicationstate;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class LoanApplicationState {
    private Long id;
    private String name;
    private String description;
}
