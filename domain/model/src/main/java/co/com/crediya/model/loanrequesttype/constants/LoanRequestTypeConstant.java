package co.com.crediya.model.loanrequesttype.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LoanRequestTypeConstant {
    CONSUMO(
            "CONSUMO",
            500_000L,
            20_000_000L,
            0.18,
            true,
            "Préstamo de consumo para cubrir gastos personales."
    ),
    LIBRE_INVERSION(
            "LIBRE_INVERSION",
            1_000_000L,
            50_000_000L,
            0.20,
            false,
            "Préstamo de libre inversión para uso flexible."
    ),
    HIPOTECARIO(
            "HIPOTECARIO",
            10_000_000L,
            500_000_000L,
            0.12,
            false,
            "Préstamo hipotecario destinado a la compra de vivienda."
    ),
    VEHICULO(
            "VEHICULO",
            5_000_000L,
            100_000_000L,
            0.15,
            true,
            "Préstamo para adquisición de vehículo nuevo o usado."
    );

    private final String name;
    private final Long minAmount;
    private final Long maxAmount;
    private final Double interestRate;
    private final Boolean autoValidation;
    private final String description;
}