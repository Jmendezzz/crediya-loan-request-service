package co.com.crediya.model.loanrequeststate;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class LoanRequestState {
    private Long id;
    private String name;
    private String description;
}
