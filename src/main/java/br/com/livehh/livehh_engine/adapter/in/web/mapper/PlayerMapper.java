package br.com.livehh.livehh_engine.adapter.in.web.mapper;

import br.com.livehh.livehh_engine.adapter.in.web.dto.PlayerDTO;
import br.com.livehh.livehh_engine.domain.entity.Player;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PlayerMapper {

    @Mapping(source = "isHero", target = "hero")
    Player toDomain(PlayerDTO playerDTO);
}
