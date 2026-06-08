package br.com.livehh.livehh_engine.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class HandHistory {
    private final String handId;
    private final String gameType;
    private final double bigBlind;
    private final List<Player> players;
    private final List<Street> streets;
    private final List<EpistemicWindow> epistemicWindows;

    public Player getHero() {
        return players.stream()
                .filter(Player::isHero)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("A mão deve ter um Hero definido"));
    }

    public boolean hasAmbiguousWindows() {
        return epistemicWindows != null && !epistemicWindows.isEmpty();
    }
}
