package br.com.livehh.livehh_engine.domain;

import br.com.livehh.livehh_engine.domain.entity.ActionType;
import br.com.livehh.livehh_engine.domain.entity.Alternative;
import br.com.livehh.livehh_engine.domain.entity.Assignment;
import br.com.livehh.livehh_engine.domain.entity.EpistemicWindow;
import br.com.livehh.livehh_engine.domain.strategy.CalibratedEvCalculator;
import br.com.livehh.livehh_engine.domain.strategy.StrictEvCalculator;
import br.com.livehh.livehh_engine.domain.valueobject.ActionMathContext;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CalibratedEvCalculatorTest {

    private final StrictEvCalculator strictCalculator = new StrictEvCalculator();
    private final CalibratedEvCalculator calibratedCalculator = new CalibratedEvCalculator(strictCalculator);

    @Test
    void deveCalcularEvCalibradoComMediaPonderadaDasAlternativasDaVisaoComputacional() {
        Assignment assign1 = new Assignment("act_1", 2, ActionType.BET, 50.0);
        Alternative alt1 = new Alternative(List.of(assign1), 0.80);

        Assignment assign2 = new Assignment("act_1", 2, ActionType.BET, 100.0);
        Alternative alt2 = new Alternative(List.of(assign2), 0.20);

        EpistemicWindow window = new EpistemicWindow("w1", "FLOP", List.of("act_1"), List.of(alt1, alt2));

        double resultadoCalibrado = calibratedCalculator.calculate(window, alternative -> {
            double simulatedBetAmount = alternative.getAssignments().get(0).getAmount();

            return new ActionMathContext(100.0, simulatedBetAmount, 0.60, 0.40);
        });

        assertEquals(83.2, resultadoCalibrado, 0.01);
    }
}