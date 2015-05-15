package refactoring.impl;

import jline.console.completer.AggregateCompleter;
import jline.console.completer.ArgumentCompleter;
import jline.console.completer.Completer;
import refactoring.Grammar;
import refactoring.Operation;
import refactoring.Rule;
import refactoring.Token;

import java.util.Collection;

/**
 * Responsible for creating configured main JLine {@link Completer}.
 * It is very useful when we want to complete user expression
 * in runtime.
 *
 * @author Alexander Krasnyaskiy
 * @version 1.0
 */
public class CompleterFactory {

    /**
     * Extract completers from operations metadata and build general
     * completer.
     *
     * @return general completer
     */
    public Completer getAggregatedCompleter() {

        Collection<Operation> operations = OperationFactory.getOperations();
        AggregateCompleter mainCmt = new AggregateCompleter();

        // go through all operation and collect completers
        // to combine them into the main console completer
        for (Operation op : operations) {
            Grammar grammar = GrammarFactory.getOperationGrammar(op);
            Rule[] rules = grammar.getRules();
            ArgumentCompleter ruleCmt = new ArgumentCompleter();

            for (Rule rule : rules) {
                Token[] tokens = rule.getTokens();
                for (Token token : tokens) {
                    Completer tokenCmt = token.getCompleter();
                    ruleCmt.getCompleters().add(tokenCmt);
                }
            }
            mainCmt.getCompleters().add(ruleCmt);
        }

        return mainCmt;
    }
}
