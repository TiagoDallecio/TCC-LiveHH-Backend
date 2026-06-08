package br.com.livehh.livehh_engine.domain.service;

import br.com.livehh.livehh_engine.domain.entity.BetCategory;
import br.com.livehh.livehh_engine.domain.valueobject.ActionMathContext;
import static br.com.livehh.livehh_engine.domain.entity.BetCategory.*;

public class BetClassificationDomainService {

    /**
     * Classifica a natureza técnica da aposta baseada na Street e na Equidade.
     */
    public BetCategory classifyHeroBet(String curretStreet, ActionMathContext context) {
        boolean isRiver = "RIVER".equalsIgnoreCase(curretStreet);

        if (isRiver){
            return context.heroExpectedEquity() >= 0.5 ? PURE_VALUE : PURE_BLUFF;
        }

        double equity = context.heroExpectedEquity();
        double foldEquity = context.opponentFoldEquity();

        if (equity >= 0.60) {
            return PURE_VALUE;
        } else if (equity >= 0.50) {
            return THIN_VALUE;
        } else if (equity >= 0.20 && foldEquity > 0.15) {
            return SEMI_BLUFF;
        } else {
            return PURE_BLUFF;
        }
    }
}
