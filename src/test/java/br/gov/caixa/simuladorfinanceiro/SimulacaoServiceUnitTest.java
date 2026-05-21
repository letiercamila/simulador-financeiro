package br.gov.caixa.simuladorfinanceiro;

import br.gov.caixa.simuladorfinanceiro.dto.request.SimulacaoRequestDto;
import br.gov.caixa.simuladorfinanceiro.dto.response.SimulacaoResponseDto;
import br.gov.caixa.simuladorfinanceiro.entity.simulacao.SimulacaoEntity;
import br.gov.caixa.simuladorfinanceiro.service.SimulacaoService;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SimulacaoServiceUnitTest {

    private EntityManager em;
    private SimulacaoService service;

    @BeforeEach
    public void setup() {
        em = Mockito.mock(EntityManager.class);
        service = new SimulacaoService();
        // inject mocked entity manager via reflection since field is package-private
        try {
            java.lang.reflect.Field f = SimulacaoService.class.getDeclaredField("em");
            f.setAccessible(true);
            f.set(service, em);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testSimularEPersistir_basic() {
        SimulacaoRequestDto req = new SimulacaoRequestDto();
        req.setValorInicial(new BigDecimal("100.00"));
        req.setTaxaJurosMensal(new BigDecimal("1.0"));
        req.setPrazoMeses(2);

        SimulacaoResponseDto resp = service.simularEPersistir(req);

        assertNotNull(resp);
        // With 1% monthly: 100.00 -> 101.00 -> 102.01 (total juros = 2.01)
        assertEquals(new BigDecimal("102.01"), resp.getValorTotal());
        assertEquals(new BigDecimal("2.01"), resp.getValorTotalJuros());
        assertEquals(2, resp.getMemoriaCalculo().size());

        // verify entity persisted
        verify(em, times(1)).persist(any(SimulacaoEntity.class));
    }

    @Test
    public void testFindById_notFound() {
        // simulate not found
        when(em.find(SimulacaoEntity.class, 123L)).thenAnswer(invocation -> null);
        SimulacaoResponseDto r = service.findById(123L);
        assertNull(r);
    }
}
