package br.com.livehh.livehh_engine.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class Alternative {
    private final List<Assignment> assignments;
    private final double weight;
}
