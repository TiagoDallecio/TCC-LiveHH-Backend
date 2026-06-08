package br.com.livehh.livehh_engine.adapter.in.web.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.List;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record AmbiguousWindowDTO(
        String windowId,
        String street,
        List<String> actionIds,
        String primarySelectionMethod,
        List<AlternativeDTO> alternatives
) {
}
