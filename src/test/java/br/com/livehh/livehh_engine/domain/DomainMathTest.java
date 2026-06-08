package br.com.livehh.livehh_engine.domain;

import br.com.livehh.livehh_engine.domain.service.BetClassificationDomainService;
import br.com.livehh.livehh_engine.domain.entity.BetCategory;
import br.com.livehh.livehh_engine.domain.strategy.StrictEvCalculator;
import br.com.livehh.livehh_engine.domain.valueobject.ActionMathContext;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DomainMathTest {

    private final StrictEvCalculator evCalculator = new StrictEvCalculator();
    private final BetClassificationDomainService classificationService = new BetClassificationDomainService();

    @Test
    void deveCalcularPotOddsCorretamente() {
        ActionMathContext context = new ActionMathContext(100.0, 50.0, 0.35, 0.0);
        assertEquals(0.333, context.calculateRequiredPotOdds(), 0.01);
    }

    @Test
    void deveCalcularEvEstritoDeUmaApostaPositiva() {
        ActionMathContext context = new ActionMathContext(100.0, 50.0, 0.60, 0.40);

        double ev = evCalculator.calculate(context);

        assertEquals(82.0, ev, 0.01);
    }

    @Test
    void deveClassificarSemiBluffNoFlop() {
        ActionMathContext context = new ActionMathContext(100.0, 50.0, 0.30, 0.40);

        BetCategory category = classificationService.classifyHeroBet("FLOP", context);

        assertEquals(BetCategory.SEMI_BLUFF, category);
    }

    @Test
    void deveClassificarPureBluffNoRiver() {
        ActionMathContext context = new ActionMathContext(100.0, 50.0, 0.30, 0.40);

        BetCategory category = classificationService.classifyHeroBet("RIVER", context);

        assertEquals(BetCategory.PURE_BLUFF, category);
    }
}