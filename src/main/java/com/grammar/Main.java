package com.grammar;

import com.grammar.grammar.Grammar;
import com.grammar.grammar.symbol.ISymbol;
import com.grammar.parser.Parser;
import javafx.util.Pair;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        Pair<Grammar, List<ISymbol>> pair = Parser.readFromFile("example.txt");

        List<List<ISymbol>> solution = Solution.getSolution(pair.getKey(), pair.getValue());
        printSolution(solution);
    }

    private static void printSolution(List<List<ISymbol>> solution) {
        if(solution == null) {
            System.out.println("К сожалению, дерево не было найдено(");
            return;
        }

        if(solution.size() == 0) {
            System.out.println("Решение было найден сразу, что не хорошо)");
            return;
        }

        for (int i = solution.size() - 1; i != 1; --i) {
            System.out.print(solution.get(i).toString() + "-->");
        }

        System.out.println(solution.get(0).toString());
    }
}
