package br.com.livehh.livehh_engine.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Player {
    private final String playerId;
    private final int seat;
    private final double startingStack;
    private final boolean hero;

    public boolean isShortStacked(double bigBlindAmount) {
        return (startingStack / bigBlindAmount < 20.0);
    }
}
