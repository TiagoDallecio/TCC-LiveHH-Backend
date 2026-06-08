package br.com.livehh.livehh_engine.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class Street {
    private final String name;
    private final List<String> boardCards;
    private final List<Action> actions;
}
