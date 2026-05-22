package br.gov.caixa.simuladorfinanceiro.rest;

import br.gov.caixa.simuladorfinanceiro.dto.request.SimulacaoRequestDto;
import br.gov.caixa.simuladorfinanceiro.dto.response.SimulacaoResponseDto;
import br.gov.caixa.simuladorfinanceiro.service.SimulacaoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.ws.rs.core.Response;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SimuladorFinanceiroRestTest {

    private SimuladorFinanceiroRest resource;
    private SimulacaoService serviceMock;

    @BeforeEach
    public void setup() throws Exception {
        resource = new SimuladorFinanceiroRest();
        serviceMock = mock(SimulacaoService.class);

        Field f = SimuladorFinanceiroRest.class.getDeclaredField("simulacaoService");
        f.setAccessible(true);
        f.set(resource, serviceMock);
    }

    @Test
    public void simularShouldReturnCreatedResponse() {
        SimulacaoResponseDto dto = SimulacaoResponseDto.builder()
                .id(42L)
                .valorTotal(new BigDecimal("102.01"))
                .valorTotalJuros(new BigDecimal("2.01"))
                .memoriaCalculo(Collections.emptyList())
                .build();

        when(serviceMock.simularEPersistir(org.mockito.ArgumentMatchers.any())).thenReturn(dto);

        SimulacaoRequestDto req = new SimulacaoRequestDto();
        req.setValorInicial(new BigDecimal("100.00"));
        req.setTaxaJurosMensal(new BigDecimal("0.02"));
        req.setPrazoMeses(1);

        Response r = resource.simular(req);
        assertEquals(201, r.getStatus());
        assertEquals("/api/simulacao/42", r.getLocation().getPath());
    }

    @Test
    public void simularWhenServiceThrowsIllegalArgumentReturnsBadRequest() {
        when(serviceMock.simularEPersistir(org.mockito.ArgumentMatchers.any()))
                .thenThrow(new IllegalArgumentException("dados invalidos"));

        SimulacaoRequestDto req = new SimulacaoRequestDto();
        req.setValorInicial(new BigDecimal("100.00"));
        req.setTaxaJurosMensal(new BigDecimal("0.02"));
        req.setPrazoMeses(1);

        Response r = resource.simular(req);
        assertEquals(400, r.getStatus());
        assertEquals("dados invalidos", r.getEntity());
    }

    @Test
    public void getSimulacaoFoundReturnsOk() {
        SimulacaoResponseDto dto = SimulacaoResponseDto.builder()
                .id(7L)
                .valorTotal(new BigDecimal("200.00"))
                .valorTotalJuros(new BigDecimal("20.00"))
                .memoriaCalculo(Collections.emptyList())
                .build();

        when(serviceMock.findById(7L)).thenReturn(dto);

        Response r = resource.getSimulacao(7L);
        assertEquals(200, r.getStatus());
        assertEquals(dto, r.getEntity());
    }

    @Test
    public void getSimulacaoNotFoundReturns404() {
        when(serviceMock.findById(999L)).thenReturn(null);

        Response r = resource.getSimulacao(999L);
        assertEquals(404, r.getStatus());
        assertNull(r.getEntity());
    }
}
