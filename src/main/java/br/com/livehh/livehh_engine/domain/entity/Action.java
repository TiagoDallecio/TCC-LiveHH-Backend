package br.com.livehh.livehh_engine.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Action {
    private final String playerId;
    private final String type; //FOLD, CALL, RAISE, CHECK
    private final double amount;
}
