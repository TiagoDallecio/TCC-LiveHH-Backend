package br.com.livehh.livehh_engine.domain.entity.usecase;

import br.com.livehh.livehh_engine.adapter.out.persistence.HandHistoryRepository;
import br.com.livehh.livehh_engine.adapter.out.persistence.entity.HandHistoryJPAEntity;
import br.com.livehh.livehh_engine.domain.entity.HandHistory;
import br.com.livehh.livehh_engine.domain.strategy.CalibratedEvCalculator;
import br.com.livehh.livehh_engine.domain.strategy.StrictEvCalculator;
import br.com.livehh.livehh_engine.domain.valueobject.ActionMathContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
public class ProcessHandHistoryUseCaseImpl implements ProcessHandHistoryUseCase {

    private final HandHistoryRepository repository;
    private final StrictEvCalculator strictEvCalculator;
    private final CalibratedEvCalculator calibratedEvCalculator;

    @Override
    @Transactional
    public String execute(HandHistory handHistory, String rawPayload){

        double finalEv = determineCalculation(handHistory);

        HandHistoryJPAEntity entity = buildHandHistoryEntity(handHistory, finalEv, rawPayload);

        repository.save(entity);

        return handHistory.getHandId();
    }

    private double determineCalculation(HandHistory handHistory) {
        double finalEv;
        if (handHistory.hasAmbiguousWindows()) {
           finalEv = calculateCalibratedEv(handHistory);
        } else {
            finalEv = calculateStrictEv();
        }

        return finalEv;
    }

    private double calculateStrictEv() {
        ActionMathContext context = new ActionMathContext(100.0, 50.0, 0.60, 0.40);

        return strictEvCalculator.calculate(context);
    }

    private double calculateCalibratedEv(HandHistory handHistory) {
        return calibratedEvCalculator.calculate(
                handHistory.getEpistemicWindows().get(0),
                alternative -> {
                    double amount = alternative.getAssignments().get(0).getAmount();
                    return new ActionMathContext(100.0, amount, 0.60, 0.40); //Mock teórico base
                }
        );
    }

    private HandHistoryJPAEntity buildHandHistoryEntity(HandHistory handHistory, double finalEv, String rawPayload) {
        return HandHistoryJPAEntity.builder()
                .handId(handHistory.getHandId())
                .gameType(handHistory.getGameType())
                .calculatedEv(finalEv)
                .processedAt(OffsetDateTime.now())
                .rawPayload(rawPayload)
                .build();
    }
}
