package br.gov.caixa.simuladorfinanceiro;

import br.gov.caixa.simuladorfinanceiro.dto.request.SimulacaoRequestDto;
import br.gov.caixa.simuladorfinanceiro.dto.response.SimulacaoResponseDto;
import br.gov.caixa.simuladorfinanceiro.service.SimulacaoService;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class SimulacaoServiceTest {

    @Inject
    SimulacaoService service;

    @Test
    public void testZeroInterest() {
        SimulacaoRequestDto req = new SimulacaoRequestDto();
        req.setValorInicial(new BigDecimal("1000.00"));
        req.setTaxaJurosMensal(new BigDecimal("0.00"));
        req.setPrazoMeses(3);

        SimulacaoResponseDto resp = service.simularEPersistir(req);

        assertNotNull(resp.getId());
        assertEquals(new BigDecimal("1000.00"), resp.getValorTotal());
        assertEquals(new BigDecimal("0.00"), resp.getValorTotalJuros());
        assertEquals(3, resp.getMemoriaCalculo().size());
        resp.getMemoriaCalculo().forEach(m -> assertEquals(new BigDecimal("0.00"), m.getJuros()));
    }

    @Test
    public void testOneMonthCalculation() {
        SimulacaoRequestDto req = new SimulacaoRequestDto();
        req.setValorInicial(new BigDecimal("1000.00"));
        req.setTaxaJurosMensal(new BigDecimal("1.00"));
        req.setPrazoMeses(1);

        SimulacaoResponseDto resp = service.simularEPersistir(req);

        assertNotNull(resp.getId());
        assertEquals(1, resp.getMemoriaCalculo().size());
        assertEquals(new BigDecimal("10.00"), resp.getValorTotalJuros());
        assertEquals(new BigDecimal("1010.00"), resp.getValorTotal());
    }

    @Test
    public void testRoundingBehavior() {
        SimulacaoRequestDto req = new SimulacaoRequestDto();
        // value that will be rounded
        req.setValorInicial(new BigDecimal("1000.005"));
        req.setTaxaJurosMensal(new BigDecimal("0.333"));
        req.setPrazoMeses(2);

        SimulacaoResponseDto resp = service.simularEPersistir(req);

        assertNotNull(resp.getId());
        // valorInicial rounded to 1000.01
        assertTrue(resp.getValorTotal().compareTo(BigDecimal.ZERO) > 0);
        assertEquals(2, resp.getMemoriaCalculo().size());
    }
}
