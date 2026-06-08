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
        String mockPayload = """
                {
                  "schema_version": "1.0.0",
                  "hand_id": "hand_2026_06_06_001",
                  "metadata": {
                    "currency_unit": "chips",
                    "generated_at": "2026-06-06T14:30:00Z",
                    "source": { "type": "synthetic", "identifier": "mock_for_java_integration" },
                    "inference_engine_version": "0.5.0-mock"
                  },
                  "table": {
                    "max_seats": 6,
                    "game_type": "NLHE",
                    "stakes": { "small_blind": 1, "big_blind": 2 },
                    "button_seat": 1
                  },
                  "players": [
                    { "seat": 1, "player_id": "p1", "starting_stack": 200, "is_hero": true }
                  ],
                  "streets": [],
                  "result": {
                    "pots": [
                      { "amount": 15, "pot_type": "main", "eligible_seats": [3, 4], "winners": [] }
                    ]
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