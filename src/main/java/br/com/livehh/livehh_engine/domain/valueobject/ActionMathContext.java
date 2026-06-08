package br.com.livehh.livehh_engine.domain.valueobject;

/**
 * Encapsula o estado matemático do pote e da equidade no momento exato
 * em que o Hero precisa tomar uma decisão. Ideal para preparar payloads
 * para engines externas como o GTO Wizard.
 */
public record ActionMathContext(
        double currentPot,
        double amountToCallOrBet,
        double heroExpectedEquity, // Força da mão (0.0 a 1.0)
        double opponentFoldEquity // Probabilidade do oponente foldar (0.0 a 1.0)
) {
    public ActionMathContext {
        if (heroExpectedEquity < 0.0 || heroExpectedEquity > 1.0) {
            throw new IllegalArgumentException("A equidade deve estar entre 0.0 e 1.0");
        }
        if (opponentFoldEquity < 0.0 || opponentFoldEquity > 1.0) {
            throw new IllegalArgumentException("A fold equity deve estar entre 0.0 e 1.0");
        }
    }

    /**
     * Pot Odds = Tamanho do Call / (Tamanho do Pote Atual + Tamanho do Call)
     * Representa a equidade mínima necessária para um Call ser lucrativo (break-even).
     */
    public double calculateRequiredPotOdds() {
        if (amountToCallOrBet == 0) return 0.0;
        return amountToCallOrBet / (currentPot + amountToCallOrBet);
    }
}
