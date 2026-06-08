package br.com.livehh.livehh_engine.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Action {
    private final String actionId;
    private final int actorSeat;
    private final ActionType type;
    private final double amount;
}
