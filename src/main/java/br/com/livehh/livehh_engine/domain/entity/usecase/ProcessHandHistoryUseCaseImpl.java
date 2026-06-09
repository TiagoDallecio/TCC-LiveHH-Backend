package br.com.livehh.livehh_engine.domain.entity.usecase;

import br.com.livehh.livehh_engine.adapter.out.persistence.HandHistoryRepository;
import br.com.livehh.livehh_engine.adapter.out.persistence.entity.HandHistoryJPAEntity;
import br.com.livehh.livehh_engine.domain.entity.Action;
import br.com.livehh.livehh_engine.domain.entity.BetCategory;
import br.com.livehh.livehh_engine.domain.entity.HandHistory;
import br.com.livehh.livehh_engine.domain.entity.Street;
import br.com.livehh.livehh_engine.domain.service.BetClassificationDomainService;
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

    private final BetClassificationDomainService classificationDomainService;

    @Override
    @Transactional
    public String execute(HandHistory handHistory, String rawPayload){

        ActionMathContext mathContext = extractRealMathContext(handHistory);

        String currentStreet = handHistory.getStreets().isEmpty() ? "PREFLOP":
                handHistory.getStreets().get(handHistory.getStreets().size() - 1).getName();

        BetCategory category = classificationDomainService.classifyHeroBet(currentStreet, mathContext);

        double finalEv = determineCalculation(handHistory, mathContext);

        HandHistoryJPAEntity entity = buildHandHistoryEntity(handHistory, finalEv, category, rawPayload);

        repository.save(entity);

        return handHistory.getHandId();
    }

    private double determineCalculation(HandHistory handHistory, ActionMathContext context) {
        double finalEv;
        if (handHistory.hasAmbiguousWindows()) {
           finalEv = calculateCalibratedEv(handHistory, context);
        } else {
            finalEv = calculateStrictEv(context);
        }

        return finalEv;
    }

    private double calculateStrictEv(ActionMathContext context) {
        return strictEvCalculator.calculate(context);
    }

    private double calculateCalibratedEv(HandHistory handHistory, ActionMathContext context) {
        return calibratedEvCalculator.calculate(
                handHistory.getEpistemicWindows().get(0),
                alternative -> {
                    double amount = alternative.getAssignments().get(0).getAmount();
                    return new ActionMathContext(context.currentPot(), amount, context.heroExpectedEquity(), context.opponentFoldEquity());
                }
        );
    }

    private ActionMathContext extractRealMathContext(HandHistory handHistory) {
        double currentPot = 0.0;
        double amountToCallOrBet = 0.0;

        for (Street street : handHistory.getStreets()) {
            for (Action action : street.getActions()) {
                currentPot += action.getAmount();

                if (action.getAmount() > 0) {
                    amountToCallOrBet = action.getAmount();
                }
            }
        }

        if (currentPot == 0) currentPot = handHistory.getBigBlind() * 1.5;

        double heroEquity = 0.60;
        double foldEquity = 0.40;

        return new ActionMathContext(currentPot, amountToCallOrBet, heroEquity, foldEquity);
    }

    private HandHistoryJPAEntity buildHandHistoryEntity(HandHistory handHistory, double finalEv, BetCategory category ,String rawPayload) {
        return HandHistoryJPAEntity.builder()
                .handId(handHistory.getHandId())
                .gameType(handHistory.getGameType())
                .calculatedEv(finalEv)
                .betCategory(category.name())
                .processedAt(OffsetDateTime.now())
                .rawPayload(rawPayload)
                .build();
    }
}
