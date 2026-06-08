package br.com.livehh.livehh_engine.domain.strategy;

import br.com.livehh.livehh_engine.domain.entity.HandHistory;
import br.com.livehh.livehh_engine.domain.valueobject.ActionMathContext;

public class StrictEvCalculator implements EvCalculationStrategy {

    @Override
    public boolean appliesTo(HandHistory handHistory) {
        return !handHistory.hasAmbiguousWindows();
    }

    @Override
    public double calculate(ActionMathContext context) {
        double pot = context.currentPot();
        double betAmount = context.amountToCallOrBet();
        double eq = context.heroExpectedEquity();
        double fe = context.opponentFoldEquity();

        double totalIfPotCalled = pot + betAmount;

        double evIfOpponentFolds = fe * pot;

        double evIfOpponentCalls = (1 -fe) * ((eq * totalIfPotCalled) - ((1- eq) * betAmount));

        return evIfOpponentFolds + evIfOpponentCalls;
    }
}
