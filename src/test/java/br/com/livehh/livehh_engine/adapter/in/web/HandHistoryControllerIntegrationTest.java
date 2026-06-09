package br.com.livehh.livehh_engine.adapter.in.web;

import br.com.livehh.livehh_engine.AbstractIntegrationTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

class HandHistoryControllerIntegrationTest extends AbstractIntegrationTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = this.port;
    }

    @Test
    void shouldAcceptMockedHandHistoryAndReturn201Created() {
        // Atualizado com a estrutura correta do JSON gerada pelo TCC
        String mockPayload = """
                {
                  "schema_version": "1.0.0",
                  "metadata": {
                    "hand_id": "hand_2026_06_06_001",
                    "table_id": "table_TCC",
                    "timestamp_start": "2026-06-06T14:30:00",
                    "timestamp_end": "2026-06-06T14:35:00",
                    "currency": "USD",
                    "game_type": "NLHE",
                    "stakes": "1/2"
                  },
                  "table": {
                    "button_seat": 1,
                    "small_blind": 1,
                    "big_blind": 2
                  },
                  "players": [
                    { 
                      "seat": 1, 
                      "player_id": "p1", 
                      "stack_initial": 200, 
                      "stack_final": 200, 
                      "is_hero": true,
                      "hole_cards": ["Ah", "Kh"] 
                    }
                  ],
                  "streets": [],
                  "result": {
                    "pot_final": 15,
                    "board": [],
                    "winners": []
                  }
                }
                """;

        given()
                .contentType(ContentType.JSON)
                .body(mockPayload)
                .when()
                .post("/api/v1/hands")
                .then()
                .statusCode(201)
                .header("Location", containsString("/api/v1/hands/hand_2026_06_06_001"));
    }
}