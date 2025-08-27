package co.com.crediya.r2dbc.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table(name = "loan_request_states")
public class LoanRequestStateEntity {
    @Id
    private Long id;
    private String name;
    private String description;
}
