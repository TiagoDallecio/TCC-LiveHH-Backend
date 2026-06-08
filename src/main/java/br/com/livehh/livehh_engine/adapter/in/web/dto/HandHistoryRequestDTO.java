package br.com.livehh.livehh_engine.adapter.in.web.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record HandHistoryRequestDTO(
        String schemaVersion,
        String handId,
        MetadataDTO metadata,
        TableDTO table,
        List<PlayerDTO> players,
        List<StreetDTO> streets,
        List<AmbiguousWindowDTO> ambiguousWindows,
        ResultDTO result
) {}
