package com.grammar;

import com.grammar.grammar.Grammar;
import com.grammar.grammar.symbol.ISymbol;
import com.grammar.grammar.symbol.NonTerminalSymbol;

import java.util.*;

import static java.util.stream.Collectors.toList;

public class Solution {
    private static class PairHelper {
        PairHelper parentHelper;
        List<ISymbol> currentPosition;
        Integer weight;

        public PairHelper(PairHelper parentHelper, List<ISymbol> currentPosition, Integer weight) {
            this.parentHelper = parentHelper;
            this.currentPosition = currentPosition;
            this.weight = weight;
        }
    }


    public static List<List<ISymbol>> getSolution(Grammar grammar, List<ISymbol> goal) {
        PriorityQueue<PairHelper> middleSolution = new PriorityQueue<>((o1, o2) -> o2.weight - o1.weight);

        middleSolution.add(new PairHelper(null, Collections.singletonList(grammar.getS()), 0));

        while (middleSolution.size() != 0) {
            PairHelper pairHelper = middleSolution.remove();

            if (isSolution(pairHelper.currentPosition, goal))
                return collectResult(pairHelper);

            if (pairHelper.currentPosition.size() <= goal.size())
                middleSolution.addAll(possibleMove(pairHelper, grammar));
        }

        return null;
    }

    private static boolean isSolution(List<ISymbol> current, List<ISymbol> goal) {
        if(current.size() == goal.size()) {
            for(int i = 0; i < goal.size(); ++i) {
                if (!current.get(i).equals(goal.get(i))) {
                    return false;
                }
            }
            return true;
        }

        return false;
    }

    private static int countWeight(List<ISymbol> current) {
        return current.stream().mapToInt(ISymbol::getWeight).sum();
    }

    private static List<PairHelper> possibleMove(PairHelper currentHelper, Grammar grammar) {
        List<PairHelper> allMovements = new ArrayList<>();
        List<ISymbol> symbols = currentHelper.currentPosition;

        for(int i = 0; i < symbols.size(); ++i) {
            if(symbols.get(i) instanceof NonTerminalSymbol) {
                NonTerminalSymbol nonTerminalSymbol = (NonTerminalSymbol)symbols.get(i);
                final int currentPosition = i;
                allMovements.addAll(
                        grammar.getRulesForNonTermSymbol(nonTerminalSymbol).stream()
                            .map(rule -> {
                                List<ISymbol> newSymbols = replace(currentPosition, symbols, rule.getTo());
                                return new PairHelper(currentHelper, newSymbols, countWeight(newSymbols));
                            }).collect(toList()));
            }
        }

        return allMovements;
    }

    private static List<ISymbol> replace(int position, List<ISymbol> currentWord, List<ISymbol> replaceTo) {
        List<ISymbol> result = new ArrayList<>(currentWord);

        for(ISymbol symbol : replaceTo) {
            if (position >= result.size()) {
                result.add(symbol);
            } else {
                result.set(position, symbol);
            }

            ++position;
        }

        return result;
    }

    private static List<List<ISymbol>> collectResult(PairHelper pairHelper) {
        List<List<ISymbol>> result = new ArrayList<>();
        result.add(pairHelper.currentPosition);

        while (pairHelper.parentHelper != null) {
            result.add(pairHelper.parentHelper.currentPosition);
            pairHelper = pairHelper.parentHelper;
        }

        return result;
    }
}
