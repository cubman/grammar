package com.grammar.grammar.symbol;

public abstract class ISymbol {

    private String symbols;

    public ISymbol(String symbols) {
        this.symbols = symbols;
    }

    public abstract int getWeight();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ISymbol iSymbol = (ISymbol) o;

        return symbols != null ? symbols.equals(iSymbol.symbols) : iSymbol.symbols == null;
    }

    @Override
    public int hashCode() {
        return symbols != null ? symbols.hashCode() : 0;
    }

    @Override
    public String toString() {
        return symbols + '\'';
    }
}
