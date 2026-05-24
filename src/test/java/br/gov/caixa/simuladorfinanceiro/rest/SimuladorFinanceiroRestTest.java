package br.gov.caixa.simuladorfinanceiro.rest;

import br.gov.caixa.simuladorfinanceiro.dto.request.SimulacaoRequestDto;
import br.gov.caixa.simuladorfinanceiro.dto.response.SimulacaoResponseDto;
import br.gov.caixa.simuladorfinanceiro.service.SimulacaoService;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.InjectMock;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.hamcrest.Matchers;

import java.math.BigDecimal;
import java.util.Collections;

import static io.restassured.RestAssured.given;
import static org.mockito.Mockito.when;

@QuarkusTest
public class SimuladorFinanceiroRestTest {

    @InjectMock
    private SimulacaoService serviceMock;

    @Test
    public void simularShouldReturnCreatedResponse() {
        SimulacaoResponseDto dto = SimulacaoResponseDto.builder()
                .id(42L)
                .valorTotal(new BigDecimal("102.01"))
                .valorTotalJuros(new BigDecimal("2.01"))
                .memoriaCalculo(Collections.emptyList())
                .build();

        when(serviceMock.simularEPersistir(ArgumentMatchers.any())).thenReturn(dto);

        SimulacaoRequestDto req = new SimulacaoRequestDto();
        req.setValorInicial(new BigDecimal("100.00"));
        req.setTaxaJurosMensal(new BigDecimal("0.02"));
        req.setPrazoMeses(1);

        given()
            .contentType(ContentType.JSON)
            .body(req)
        .when()
            .post("/api/simulacao")
        .then()
            .statusCode(201)
            .header("Location", Matchers.containsString("/api/simulacao/42"));
    }

    @Test
    public void simularWhenServiceThrowsIllegalArgumentReturnsBadRequest() {
        when(serviceMock.simularEPersistir(ArgumentMatchers.any()))
                .thenThrow(new IllegalArgumentException("dados invalidos"));

        SimulacaoRequestDto req = new SimulacaoRequestDto();
        req.setValorInicial(new BigDecimal("100.00"));
        req.setTaxaJurosMensal(new BigDecimal("0.02"));
        req.setPrazoMeses(1);

        given()
            .contentType(ContentType.JSON)
            .body(req)
        .when()
            .post("/api/simulacao")
        .then()
            .statusCode(400)
            .body(Matchers.is("dados invalidos"));
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

        given()
        .when()
            .get("/api/simulacao/7")
        .then()
            .statusCode(200)
            .body("id", Matchers.is(7));
    }

    @Test
    public void getSimulacaoNotFoundReturns404() {
        when(serviceMock.findById(999L)).thenReturn(null);

        given()
        .when()
            .get("/api/simulacao/999")
        .then()
            .statusCode(404);
    }

    @Test
    public void simularWhenServiceThrowsGenericExceptionReturnsInternalServerError() {
        when(serviceMock.simularEPersistir(ArgumentMatchers.any()))
                .thenThrow(new RuntimeException("Erro inesperado"));

        SimulacaoRequestDto req = new SimulacaoRequestDto();
        req.setValorInicial(new BigDecimal("100.00"));
        req.setTaxaJurosMensal(new BigDecimal("0.02"));
        req.setPrazoMeses(1);

        given()
            .contentType(ContentType.JSON)
            .body(req)
        .when()
            .post("/api/simulacao")
        .then()
            .statusCode(500)
            .body(Matchers.is("Erro ao processar simulação"));
    }

    @Test
    public void getSimulacaoWhenServiceThrowsIllegalArgumentReturnsBadRequest() {
        when(serviceMock.findById(ArgumentMatchers.anyLong()))
                .thenThrow(new IllegalArgumentException("ID inválido"));

        given()
        .when()
            .get("/api/simulacao/1")
        .then()
            .statusCode(400)
            .body(Matchers.is("ID inválido"));
    }

    @Test
    public void getSimulacaoWhenServiceThrowsGenericExceptionReturnsInternalServerError() {
        when(serviceMock.findById(ArgumentMatchers.anyLong()))
                .thenThrow(new RuntimeException("Erro inesperado ao buscar"));

        given()
        .when()
            .get("/api/simulacao/1")
        .then()
            .statusCode(500)
            .body(Matchers.is("Erro ao buscar simulação"));
    }
}