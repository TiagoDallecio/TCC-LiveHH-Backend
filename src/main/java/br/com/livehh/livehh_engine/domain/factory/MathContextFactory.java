package br.com.livehh.livehh_engine.domain.factory;

import br.com.livehh.livehh_engine.domain.entity.Alternative;
import br.com.livehh.livehh_engine.domain.valueobject.ActionMathContext;

public interface MathContextFactory {
    /**
     * Extrai os parâmetros matemáticos do estado do jogo a partir de uma hipótese epistêmica.
     */
    ActionMathContext createFrom(Alternative alternative);
}

