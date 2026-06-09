package br.com.livehh.livehh_engine.adapter.in.web.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record PlayerDTO(
        Integer seat,
        String playerId,
        Boolean isHero,
        Long stackInitial,
        Long stackFinal,
        List<String> holeCards
) {
}
