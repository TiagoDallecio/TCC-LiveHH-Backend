package br.com.livehh.livehh_engine.adapter.in.web.mapper;

import br.com.livehh.livehh_engine.adapter.in.web.dto.HandHistoryRequestDTO;
import br.com.livehh.livehh_engine.adapter.in.web.dto.PlayerDTO;
import br.com.livehh.livehh_engine.domain.entity.HandHistory;
import br.com.livehh.livehh_engine.domain.entity.Player;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface HandHistoryMapper {

    @Mapping(source = "table.gameType", target = "gameType")
    @Mapping(source = "table.stakes.bigBlind", target = "bigBlind")
    HandHistory toDomain(HandHistoryRequestDTO dto);

    @Mapping(source = "isHero", target = "hero")
    Player toPlayerDomain(PlayerDTO playerDTO);
}
