package br.com.livehh.livehh_engine.adapter.in.web.mapper;

import br.com.livehh.livehh_engine.adapter.in.web.dto.*;
import br.com.livehh.livehh_engine.domain.entity.ActionType;
import br.com.livehh.livehh_engine.domain.entity.HandHistory;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HandHistoryMapperTest {

    private final HandHistoryMapper mapper = Mappers.getMapper(HandHistoryMapper.class);

    public HandHistoryMapperTest() {
        PlayerMapper playerMapper = Mappers.getMapper(PlayerMapper.class);
        ReflectionTestUtils.setField(mapper, "playerMapper", playerMapper);
    }

    @Test
    void deveMapearDtoParaDominioDeFormaIsolada() {
        // Arrange
        TableDTO tableDTO = new TableDTO(6, "NLHE", new StakesDTO(1L, 2L, 0L), 1);
        PlayerDTO playerDTO = new PlayerDTO(1, "p1", 200L, true, List.of("Ah", "Kh"));
        ActionDTO actionDTO = new ActionDTO("act_1", 1, "RAISE", 10L, "high", null);
        StreetDTO streetDTO = new StreetDTO("PREFLOP", List.of(), List.of(actionDTO));

        AlternativeDTO altDTO = new AlternativeDTO(List.of(new AssignmentDTO("act_1", 1, "CALL", 2L)), 0.15);
        AmbiguousWindowDTO windowDTO = new AmbiguousWindowDTO("w1", "PREFLOP", List.of("act_1"), "yolov8_model", List.of(altDTO));

        HandHistoryRequestDTO dto = new HandHistoryRequestDTO(
                "1.0",
                "hand_mock",
                null,
                tableDTO,
                List.of(playerDTO),
                List.of(streetDTO),
                List.of(windowDTO),
                null
        );

        // Act
        HandHistory domain = mapper.toDomain(dto);

        // Assert
        assertNotNull(domain);
        assertEquals("hand_mock", domain.getHandId());
        assertEquals("NLHE", domain.getGameType());
        assertEquals(2.0, domain.getBigBlind());

        assertTrue(domain.getHero().isHero());
        assertEquals(200.0, domain.getHero().getStartingStack());

        assertEquals(1, domain.getStreets().size());
        assertEquals(ActionType.RAISE, domain.getStreets().get(0).getActions().get(0).getType());

        assertTrue(domain.hasAmbiguousWindows());
        assertEquals(0.15, domain.getEpistemicWindows().get(0).getAlternatives().get(0).getWeight());
    }
}