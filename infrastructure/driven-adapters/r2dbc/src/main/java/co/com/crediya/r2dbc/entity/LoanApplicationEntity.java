package co.com.crediya.r2dbc.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table(name = "loan_applications")
public class LoanApplicationEntity {
    @Id
    private Long id;
    private String customerIdentityNumber;
    private Double amount;
    private Integer termInMonths;
    private Long typeId;
    private Long stateId;
}
