package br.com.livehh.livehh_engine.adapter.in.web;

import br.com.livehh.livehh_engine.adapter.in.web.dto.HandHistoryRequestDTO;
import br.com.livehh.livehh_engine.adapter.in.web.mapper.HandHistoryMapper;
import br.com.livehh.livehh_engine.domain.entity.HandHistory;
import br.com.livehh.livehh_engine.domain.entity.usecase.ProcessHandHistoryUseCase;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RequiredArgsConstructor
@RestController
public class HandHistoryController {

    private final ProcessHandHistoryUseCase processHandHistoryUseCase;
    private final HandHistoryMapper mapper;
    private final ObjectMapper objectMapper;


    @PostMapping("/api/v1/hands")
    public ResponseEntity<Void> ingestHandHistory(@RequestBody HandHistoryRequestDTO requestDTO) throws JsonProcessingException {

        String rawJson = objectMapper.writeValueAsString(requestDTO);

        HandHistory handHistoryDomain = mapper.toDomain(requestDTO);

        String resultId = processHandHistoryUseCase.execute(handHistoryDomain, rawJson);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(resultId)
                .toUri();

        return ResponseEntity.created(location).build();
    }
}