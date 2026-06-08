package br.com.livehh.livehh_engine.domain.strategy;

import br.com.livehh.livehh_engine.domain.entity.Alternative;
import br.com.livehh.livehh_engine.domain.entity.EpistemicWindow;
import br.com.livehh.livehh_engine.domain.factory.MathContextFactory;
import br.com.livehh.livehh_engine.domain.valueobject.ActionMathContext;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CalibratedEvCalculator {

    private final StrictEvCalculator baseCalculator;

    public double calculate(EpistemicWindow window, MathContextFactory contextFactory) {
        double calibratedEv = 0.0;

        for (Alternative alternative : window.getAlternatives()){

            ActionMathContext context = contextFactory.createFrom(alternative);

            double isolatedEv = baseCalculator.calculate(context);

            calibratedEv += isolatedEv * alternative.getWeight();
        }

        return calibratedEv;
    }
}
