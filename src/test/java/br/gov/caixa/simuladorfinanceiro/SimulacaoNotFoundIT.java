package br.gov.caixa.simuladorfinanceiro;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@QuarkusTest
public class SimulacaoNotFoundIT {

    @Test
    public void testGetNotFound() {
        given()
                .when().get("/api/simulacao/999999")
                .then()
                .statusCode(404);
    }
}
