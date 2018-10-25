package com.grammar.grammar;

import com.grammar.grammar.symbol.ISymbol;
import com.grammar.grammar.symbol.NonTerminalSymbol;

import java.util.List;

public class Rule {
    private NonTerminalSymbol from;
    private List<ISymbol> to;

    public Rule(NonTerminalSymbol from, List<ISymbol> to) {
        this.from = from;
        this.to = to;
    }

    public NonTerminalSymbol getFrom() {
        return from;
    }

    public List<ISymbol> getTo() {
        return to;
    }
}
