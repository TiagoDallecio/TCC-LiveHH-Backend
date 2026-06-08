package br.com.livehh.livehh_engine.domain.entity.usecase;

import br.com.livehh.livehh_engine.domain.entity.HandHistory;

public interface ProcessHandHistoryUseCase {
    String execute(HandHistory handHistory, String rawPayload);
}
