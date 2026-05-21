package br.gov.caixa.simuladorfinanceiro;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusTest
public class SimulacaoResourceIT {

    @Test
    public void testCreateAndGetSimulation() {
        String requestJson = "{\"valorInicial\": 1000.00, \"taxaJurosMensal\": 1.0, \"prazoMeses\": 2 }";

        // Create simulation -> expect 201 Created
        String location = given()
                .header("Content-Type", "application/json")
                .body(requestJson)
                .when().post("/api/simulacao")
                .then()
                .statusCode(201)
                .header("Location", notNullValue())
                .body("id", notNullValue())
                .body("valorTotal", notNullValue())
                .extract()
                .header("Location");

        // Extract id from location
        String[] parts = location.split("/");
        String idStr = parts[parts.length - 1];
        Long id = Long.valueOf(idStr);

        // Retrieve by id
        given()
                .when().get("/api/simulacao/" + id)
                .then()
                .statusCode(200)
                .body("id", is(id.intValue()))
                .body("memoriaCalculo", hasSize(2))
                .body("valorTotalJuros", notNullValue());
    }

    @Test
    public void testCreateSimulationBadRequest() {
        // Missing valorInicial
        String invalidJson = "{ \"taxaJurosMensal\": 1.0, \"prazoMeses\": 2 }";

        given()
                .header("Content-Type", "application/json")
                .body(invalidJson)
                .when().post("/api/simulacao")
                .then()
                .statusCode(400);
    }
}
