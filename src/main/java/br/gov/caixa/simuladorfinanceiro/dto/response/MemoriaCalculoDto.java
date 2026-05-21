package br.gov.caixa.simuladorfinanceiro.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class MemoriaCalculoDto {
    private Integer mes;
    private BigDecimal saldoInicial;
    private BigDecimal juros;
    private BigDecimal saldoFinal;
}
