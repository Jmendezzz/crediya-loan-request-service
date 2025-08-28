package co.com.crediya.api.rest.loanapplication.constant;

public class LoanApplicationValidationMessage {
    public static final String IDENTITY_NUMBER_REQUIRED = "El número de identificación del cliente es obligatorio";
    public static final String AMOUNT_REQUIRED = "El monto solicitado es obligatorio";
    public static final String AMOUNT_POSITIVE = "El monto debe ser un valor positivo";
    public static final String TERM_REQUIRED = "El plazo del préstamo es obligatorio";
    public static final String TERM_MIN = "El plazo debe ser al menos de 1 mes";
    public static final String TYPE_REQUIRED = "El tipo de préstamo es obligatorio";
}