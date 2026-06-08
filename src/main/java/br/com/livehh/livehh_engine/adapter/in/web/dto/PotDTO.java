package br.com.livehh.livehh_engine.adapter.in.web.dto;

import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record PotDTO(
        Long amount,
        String potType,
        List<Integer> eligibleSeats,
        List<WinnerDTO> winners
) {
}
