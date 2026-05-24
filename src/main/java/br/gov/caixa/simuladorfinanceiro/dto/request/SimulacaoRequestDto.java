package br.gov.caixa.simuladorfinanceiro.dto.request;

import lombok.Data;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

@Data
public class SimulacaoRequestDto {

    @NotNull(message = "O valor inicial é obrigatório")
    @DecimalMin(value = "0.01", message = "O valor inicial deve ser de pelo menos 0.01")
    @Digits(integer = 12, fraction = 2, message = "O valor inicial deve ter no máximo 12 dígitos e 2 casas decimais")
    private BigDecimal valorInicial;

    @NotNull(message = "A taxa de juros é obrigatória")
    @DecimalMin(value = "0.0", message = "A taxa de juros não pode ser negativa")
    @DecimalMax(value = "100.0", message = "A taxa de juros mensal não pode exceder 100%")
    @Digits(integer = 3, fraction = 4, message = "A taxa de juros deve ter no máximo 3 dígitos e 4 casas decimais")
    private BigDecimal taxaJurosMensal;

    @NotNull(message = "O prazo em meses é obrigatório")
    @Min(value = 1, message = "O prazo mínimo é de 1 mês")
    private Integer prazoMeses;
}