package refactoring.impl;

import refactoring.Grammar;
import refactoring.Operation;
import refactoring.Rule;
import refactoring.Token;

/**
 * The factory class creates an operation grammar using
 * operation metadata, which is contained in operation
 * annotations.
 *
 * @author Alexander Krasnyaskiy
 * @version 1.0
 */
public class GrammarFactory {

    public static Grammar getOperationGrammar(Operation operation) {
        return new Grammar() {
            @Override
            public Rule[] getRules() {
                return new Rule[]{new Rule() {
                    @Override
                    public Token[] getTokens() {
                        return new Token[]{};
                    }
                }};
            }
        };
    }
}
