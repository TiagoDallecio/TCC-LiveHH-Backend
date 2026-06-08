package br.com.livehh.livehh_engine.adapter.in.web.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.time.OffsetDateTime;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record MetadataDTO(
        String currencyUnit,
        OffsetDateTime generatedAt,
        SourceDTO source,
        String inferenceEngineVersion
) {
}
