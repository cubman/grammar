package com.grammar.parser;

import com.grammar.grammar.Grammar;
import com.grammar.grammar.Rule;
import com.grammar.grammar.symbol.ISymbol;
import com.grammar.grammar.symbol.NonTerminalSymbol;
import com.grammar.grammar.symbol.TerminalSymbol;
import javafx.util.Pair;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Parser {

    public static Pair<Grammar, List<ISymbol>> readFromFile(String fileNmae) {
        ClassLoader classLoader = Parser.class.getClassLoader();
        File file = new File(classLoader.getResource(fileNmae).getFile());
        Grammar grammar = new Grammar();
        List<ISymbol> goal = null;
        try(
        FileInputStream fis = new FileInputStream(file);
        InputStreamReader isr = new InputStreamReader(fis);
        BufferedReader br = new BufferedReader(isr)) {
            String line;
            while ((line = br.readLine()) != null) {
                switch (line.substring(0, 1)) {
                    case "N" : grammar.setN(parseN(line.substring(2))); break;
                    case "T" : grammar.setT(parseT(line.substring(2))); break;
                    case "P" : grammar.setP(parseP(line.substring(2), grammar)); break;
                    case "S" : grammar.setS(parseS(line.substring(2), grammar)); break;
                    case "X" : goal = parseX(line.substring(2), grammar); break;
                }
                System.out.println(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new Pair<>(grammar, goal);
    }

    private static List<ISymbol> parseX(String lineX, Grammar grammar) throws Exception {
        List<ISymbol> symbols = new ArrayList<>();

        String[] strings = lineX.substring(3, lineX.length() - 2).replaceAll(" ", "").split("");

        List<Rule> rules = new ArrayList<>();
        String currentString = "";
        for(String string : strings) {
            currentString += string;
            int indN = grammar.getN().indexOf(new NonTerminalSymbol(currentString));
            if(indN != -1) {
                throw new Exception("nonTerminalSymbol не допустим");
            }

            int indT = grammar.getT().indexOf(new TerminalSymbol(currentString));
            if(indT != -1) {
                TerminalSymbol terminalSymbol = grammar.getT().get(indT);
                symbols.add(terminalSymbol);
                currentString = "";
            }
        }

        if(currentString != "") {
            throw new Exception("was not parsed: " + currentString);
        }

        return symbols;
    }

    private static List<TerminalSymbol> parseT(String lineT) {
        String[] strings = lineT.substring(3, lineT.length() - 2).replaceAll(" ", "").split(",");

        List<TerminalSymbol> terminalSymbols = new ArrayList<>();
        for(String string : strings) {
            terminalSymbols.add(new TerminalSymbol(string));
        }

        return terminalSymbols;
    }

    private static List<NonTerminalSymbol> parseN(String lineN) {
        String[] strings = lineN.substring(3, lineN.length() - 2).replaceAll(" ", "").split(",");

        List<NonTerminalSymbol> nonTerminalSymbols = new ArrayList<>();
        for(String string : strings) {
            nonTerminalSymbols.add(new NonTerminalSymbol(string));
        }

        return nonTerminalSymbols;
    }

    private static List<Rule> parseP(String lineP, Grammar grammar) throws Exception {
        String[] strings = lineP.substring(3, lineP.length() - 2).replaceAll(" ", "").split(",");

        List<Rule> rules = new ArrayList<>();
        for(String string : strings) {
            String[] subStrings = string.replaceAll("->", ",").split(",");
            int ind = grammar.getN().indexOf(new NonTerminalSymbol(subStrings[0]));
            if(ind == -1)
                throw new Exception("index == -1");
            NonTerminalSymbol nonTerminalSymbol = grammar.getN().get(ind);
            List<ISymbol> rightPart = parseRightPart(subStrings[1], grammar);
            Rule rule = new Rule(nonTerminalSymbol, rightPart);
            rules.add(rule);
        }

        return rules;
    }

    private static List<ISymbol> parseRightPart(String rightRule, Grammar grammar) throws Exception {
        int ind = 0;
        int length = 1;
        int size = rightRule.length();
        List<ISymbol> symbolList = new ArrayList<>();

        while (true) {

            if(ind >= size)
                break;

            String symbols = rightRule.substring(ind, ind + length);
            int indexOfT = grammar.getT().indexOf(new TerminalSymbol(symbols));
            int indexOfN = grammar.getN().indexOf(new NonTerminalSymbol(symbols));

            if(indexOfT * indexOfN <= 0)
            {
                ind += length;
                length = 1;

                if(indexOfT != -1)
                    symbolList.add(grammar.getT().get(indexOfT));
                else
                    if(indexOfN != -1)
                symbolList.add(grammar.getN().get(indexOfN));
                else
                    throw new Exception("was not found");

                continue;
            }

            ++length;
        }

        return symbolList;
    }

    private static NonTerminalSymbol parseS(String lineS, Grammar grammar) {
        String[] strings = lineS.substring(3, lineS.length() - 2).replaceAll(" ", "").split("");

        int indexOf = grammar.getN().indexOf(new NonTerminalSymbol(strings[0]));

        return grammar.getN().get(indexOf);
    }
}
