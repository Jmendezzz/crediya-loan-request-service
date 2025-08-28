package co.com.crediya.r2dbc.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("loan_application_types")
public class LoanApplicationTypeEntity {
    @Id
    private Long id;
    private String name;
    private String description;
    private Long minAmount;
    private Long maxAmount;
    private Double interestRate;
    private Boolean autoValidation;
}
