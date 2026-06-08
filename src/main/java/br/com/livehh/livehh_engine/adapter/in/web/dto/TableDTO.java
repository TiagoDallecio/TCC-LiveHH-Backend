package br.com.livehh.livehh_engine.adapter.in.web.dto;

import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record TableDTO(
        Integer maxSeats,
        String gameType,
        StakesDTO stakes,
        Integer buttonSeat
) {
}
