package co.com.crediya.r2dbc.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("loan_request_types")
public class LoanRequestTypeEntity {
    @Id
    private Long id;
    private String name;
    private String description;
    private Long minAmount;
    private Long maxAmount;
    private Double interestRate;
    private Boolean autoValidation;
}
