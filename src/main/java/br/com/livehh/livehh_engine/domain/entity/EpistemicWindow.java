package br.com.livehh.livehh_engine.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class EpistemicWindow {
    private final String windowId;
    private final String street;
    private final List<String> actionIds;
    private final List<Alternative> alternatives;
}
