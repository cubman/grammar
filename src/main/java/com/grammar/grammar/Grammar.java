package com.grammar.grammar;

import com.grammar.grammar.symbol.NonTerminalSymbol;
import com.grammar.grammar.symbol.TerminalSymbol;

import java.util.List;
import java.util.stream.Collectors;

public class Grammar {
    List<NonTerminalSymbol> N;
    List<TerminalSymbol> T;
    List<Rule> P;
    NonTerminalSymbol S;


    public void setN(List<NonTerminalSymbol> n) {
        N = n;
    }

    public void setT(List<TerminalSymbol> t) {
        T = t;
    }

    public void setP(List<Rule> p) {
        P = p;
    }

    public void setS(NonTerminalSymbol s) {
        S = s;
    }

    public Grammar() {

    }

    public Grammar(List<NonTerminalSymbol> n, List<TerminalSymbol> t, List<Rule> p, NonTerminalSymbol s) {
        N = n;
        T = t;
        P = p;
        S = s;
    }

    public List<NonTerminalSymbol> getN() {
        return N;
    }

    public List<TerminalSymbol> getT() {
        return T;
    }

    public List<Rule> getP() {
        return P;
    }

    public NonTerminalSymbol getS() {
        return S;
    }

    public List<Rule> getRulesForNonTermSymbol(NonTerminalSymbol nonTerminalSymbol) {
        return P.stream().filter(rule -> rule.getFrom().equals(nonTerminalSymbol)).collect(Collectors.toList());
    }
}
