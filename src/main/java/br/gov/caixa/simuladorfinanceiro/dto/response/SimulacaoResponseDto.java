package br.gov.caixa.simuladorfinanceiro.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class SimulacaoResponseDto {
    private Long id;
    private BigDecimal valorTotal;
    private BigDecimal valorTotalJuros;
    private List<MemoriaCalculoDto> memoriaCalculo;
}
