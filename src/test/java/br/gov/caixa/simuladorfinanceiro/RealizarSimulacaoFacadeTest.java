package br.gov.caixa.simuladorfinanceiro;

import br.gov.caixa.simuladorfinanceiro.dto.request.SimulacaoRequestDto;
import br.gov.caixa.simuladorfinanceiro.dto.response.SimulacaoResponseDto;
import br.gov.caixa.simuladorfinanceiro.facade.RealizarSimulacaoFacade;
import br.gov.caixa.simuladorfinanceiro.service.SimulacaoService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class RealizarSimulacaoFacadeTest {

    @Test
    public void testFacadeDelegatesToService() {
        SimulacaoService mocked = Mockito.mock(SimulacaoService.class);
        RealizarSimulacaoFacade facade = new RealizarSimulacaoFacade(mocked);

        SimulacaoRequestDto req = new SimulacaoRequestDto();
        req.setValorInicial(new BigDecimal("10"));
        req.setTaxaJurosMensal(new BigDecimal("0"));
        req.setPrazoMeses(1);

        SimulacaoResponseDto resp = SimulacaoResponseDto.builder()
                .valorTotal(new BigDecimal("10.00"))
                .build();

        when(mocked.simularEPersistir(req)).thenReturn(resp);

        SimulacaoResponseDto result = facade.realizarSimulacao(req);

        assertEquals(resp.getValorTotal(), result.getValorTotal());
    }
}
