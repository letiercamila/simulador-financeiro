package br.gov.caixa.simuladorfinanceiro.dto.request;

import lombok.Data;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class SimulacaoRequestDto {

    @NotNull
    @DecimalMin(value = "0.01", inclusive = true)
    private BigDecimal valorInicial;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = true)
    private BigDecimal taxaJurosMensal;

    @NotNull
    @Min(1)
    private Integer prazoMeses;
}