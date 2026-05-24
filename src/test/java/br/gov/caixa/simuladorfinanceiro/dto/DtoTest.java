package br.gov.caixa.simuladorfinanceiro.dto;

import br.gov.caixa.simuladorfinanceiro.dto.request.SimulacaoRequestDto;
import br.gov.caixa.simuladorfinanceiro.dto.response.SimulacaoResponseDto;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.util.Collections;
import static org.junit.jupiter.api.Assertions.*;

public class DtoTest {

    @Test
    public void testRequestDto() {
        SimulacaoRequestDto dto = new SimulacaoRequestDto();
        dto.setValorInicial(BigDecimal.TEN);
        dto.setTaxaJurosMensal(BigDecimal.ONE);
        dto.setPrazoMeses(12);

        assertEquals(BigDecimal.TEN, dto.getValorInicial());
        assertNotNull(dto.toString());
        assertEquals(dto, dto);
        assertNotNull(dto.hashCode());
        assertNotEquals(dto, new SimulacaoRequestDto());
    }

    @Test
    public void testResponseDto() {
        SimulacaoResponseDto dto = SimulacaoResponseDto.builder()
                .id(1L)
                .valorTotal(BigDecimal.TEN)
                .memoriaCalculo(Collections.emptyList())
                .build();

        assertEquals(1L, dto.getId());
        assertEquals(BigDecimal.TEN, dto.getValorTotal());
        assertNotNull(dto.getMemoriaCalculo());
        assertNotNull(dto.toString());
        assertNotNull(dto.hashCode());
        assertNotEquals(dto, SimulacaoResponseDto.builder().id(2L).build());
    }
}