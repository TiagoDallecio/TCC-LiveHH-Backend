package br.com.livehh.livehh_engine.adapter.in.web.dto;

import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record AssignmentDTO(
        String actionId,
        Integer actorSeat,
        String kind,
        Long amount
) {
}
