package br.com.livehh.livehh_engine.adapter.in.web.mapper;

import br.com.livehh.livehh_engine.adapter.in.web.dto.*;
import br.com.livehh.livehh_engine.domain.entity.ActionType;
import br.com.livehh.livehh_engine.domain.entity.HandHistory;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
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

        MetadataDTO metadataDTO = new MetadataDTO(
                "hand_mock",
                "table_TCC",
                LocalDateTime.now(),
                LocalDateTime.now(),
                "USD",
                "NLHE",
                "1/2"
        );

        // 2. TableDTO agora recebe apenas buttonSeat, smallBlind e bigBlind
        TableDTO tableDTO = new TableDTO(1, 1L, 2L);

        // 3. PlayerDTO agora recebe stackInitial e stackFinal
        PlayerDTO playerDTO = new PlayerDTO(1, "p1", true, 200L, 200L, List.of("Ah", "Kh"));

        ActionDTO actionDTO = new ActionDTO("act_1", 1, "RAISE", 10L, "0.95", null);
        StreetDTO streetDTO = new StreetDTO("PREFLOP", List.of(), List.of(actionDTO));

        // 4. AssignmentDTO agora não tem mais actionId, apenas actorSeat, kind e amount
        AlternativeDTO altDTO = new AlternativeDTO(List.of(new AssignmentDTO(1, "CALL", 2L)), 0.15);
        AmbiguousWindowDTO windowDTO = new AmbiguousWindowDTO("w1", "PREFLOP", List.of("act_1"), "lexicographic_seat", List.of(altDTO));

        // 5. HandHistoryRequestDTO atualizado sem o hand_id na raiz
        HandHistoryRequestDTO dto = new HandHistoryRequestDTO(
                "1.0",
                metadataDTO,
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