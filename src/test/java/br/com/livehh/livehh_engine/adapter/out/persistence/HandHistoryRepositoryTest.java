package br.com.livehh.livehh_engine.adapter.out.persistence;

import br.com.livehh.livehh_engine.AbstractIntegrationTest;
import br.com.livehh.livehh_engine.adapter.out.persistence.entity.HandHistoryJPAEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class HandHistoryRepositoryTest extends AbstractIntegrationTest {

    @Autowired
    private HandHistoryRepository repository;

    @Test
    @Transactional
    void deveSalvarERecuperarEntidadeComColunaJsonb() {
        // Arrange
        String mockJsonPayload = """
                {
                  "schema_version": "1.0.0",
                  "hand_id": "hand_postgres_001",
                  "table": {
                    "game_type": "NLHE"
                  }
                }
                """;

        HandHistoryJPAEntity entity = HandHistoryJPAEntity.builder()
                .handId("hand_postgres_001")
                .gameType("NLHE")
                .calculatedEv(83.2)
                .processedAt(OffsetDateTime.now())
                .rawPayload(mockJsonPayload)
                .build();

        // Act
        repository.saveAndFlush(entity);

        // Assert
        Optional<HandHistoryJPAEntity> savedEntityOpt = repository.findById("hand_postgres_001");

        assertTrue(savedEntityOpt.isPresent());
        HandHistoryJPAEntity savedEntity = savedEntityOpt.get();

        assertEquals("hand_postgres_001", savedEntity.getHandId());
        assertEquals(83.2, savedEntity.getCalculatedEv());

        assertNotNull(savedEntity.getRawPayload());
        assertTrue(savedEntity.getRawPayload().contains("\"schema_version\": \"1.0.0\""));
    }
}