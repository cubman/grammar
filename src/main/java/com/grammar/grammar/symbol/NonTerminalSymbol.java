package com.grammar.grammar.symbol;

public class NonTerminalSymbol extends ISymbol {

    public NonTerminalSymbol(String symbol) {
        super(symbol);
    }

    public int getWeight() {
        return 10;
    }

    @Override
    public String toString() {
        return symbols + '\'';
    }
}
