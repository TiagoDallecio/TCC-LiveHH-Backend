package br.com.livehh.livehh_engine.domain.strategy;

import br.com.livehh.livehh_engine.domain.entity.HandHistory;
import br.com.livehh.livehh_engine.domain.valueobject.ActionMathContext;

public interface EvCalculationStrategy {

    boolean appliesTo(HandHistory handHistory);

    /**
     * Calcula o EV (Expected Value) da jogada em fichas/BBs.
     */
    double calculate(ActionMathContext context);
}
