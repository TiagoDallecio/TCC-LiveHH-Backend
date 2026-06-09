package br.com.livehh.livehh_engine.adapter.in.web.mapper;

import br.com.livehh.livehh_engine.adapter.in.web.dto.*;
import br.com.livehh.livehh_engine.domain.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {PlayerMapper.class})
public interface HandHistoryMapper {

    @Mapping(source = "metadata.handId", target = "handId")
    @Mapping(source = "metadata.gameType", target = "gameType")
    @Mapping(source = "table.bigBlind", target = "bigBlind")
    @Mapping(source = "ambiguousWindows", target = "epistemicWindows")
    HandHistory toDomain(HandHistoryRequestDTO dto);

    @Mapping(source = "kind", target = "type")
    Action toActionDomain(ActionDTO actionDTO);

    Street toStreetDomain(StreetDTO streetDTO);

    EpistemicWindow toEpistemicWindowDomain(AmbiguousWindowDTO ambiguousWindowDTO);

    Alternative toAlternativeDomain(AlternativeDTO alternativeDTO);

    @Mapping(source = "kind", target = "type")
    Assignment toAssignmentDomain(AssignmentDTO assignmentDTO);
}