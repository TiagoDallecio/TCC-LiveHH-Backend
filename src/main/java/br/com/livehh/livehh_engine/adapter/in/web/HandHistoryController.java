package br.com.livehh.livehh_engine.adapter.in.web;

import br.com.livehh.livehh_engine.adapter.in.web.dto.HandHistoryRequestDTO;
import br.com.livehh.livehh_engine.adapter.in.web.mapper.HandHistoryMapper;
import br.com.livehh.livehh_engine.domain.entity.HandHistory;
import br.com.livehh.livehh_engine.domain.entity.usecase.ProcessHandHistoryUseCase;
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

    // public HandHistoryController(ProcessHandHistoryUseCase processHandHistoryUseCase) {
    //     this.processHandHistoryUseCase = processHandHistoryUseCase;
    // }

    @PostMapping("/api/v1/hands")
    public ResponseEntity<Void> ingestHandHistory(@RequestBody HandHistoryRequestDTO requestDTO) {

        // 1. Opcional: Validação de Bean (JSR 380) no DTO
        // 2. Mapear o DTO para a Entidade de Domínio (ex: via MapStruct ou Mapper estático)
        // 3. Chamar a camada de Domínio, onde os "Strategy" (Strict ou Calibrated) calcularão o EV

        HandHistory handHistoryDomain = mapper.toDomain(requestDTO);

        String resultId = processHandHistoryUseCase.execute(handHistoryDomain);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(resultId)
                .toUri();

        return ResponseEntity.created(location).build();
    }
}