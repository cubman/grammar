package com.grammar.grammar.symbol;

public class TerminalSymbol extends ISymbol {
    public TerminalSymbol(String symbols) {
        super(symbols);
    }

    public int getWeight() {
        return 100;
    }

    @Override
    public String toString() {
        return symbols;
    }
}
