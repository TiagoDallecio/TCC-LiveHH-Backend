package br.com.livehh.livehh_engine.adapter.in.web.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.time.LocalDateTime;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record MetadataDTO(
        String handId,
        String tableId,
        LocalDateTime timestampStart,
        LocalDateTime timestampEnd,
        String stakes,
        String gameType,
        String currency
) {
}
