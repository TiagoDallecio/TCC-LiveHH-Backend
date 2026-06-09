package br.com.livehh.livehh_engine.adapter.in.web.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record HandHistoryRequestDTO(
        String schemaVersion,
        MetadataDTO metadata,
        TableDTO table,
        List<PlayerDTO> players,
        List<StreetDTO> streets,
        List<AmbiguousWindowDTO> ambiguousWindows,
        ResultDTO result
) {}
