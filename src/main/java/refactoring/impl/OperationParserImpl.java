package refactoring.impl;

import refactoring.Grammar;
import refactoring.Operation;
import refactoring.OperationParser;
import refactoring.Rule;
import refactoring.Token;

/**
 * @author Alexander Krasnyanskiy
 * @version 1.0
 */
public class OperationParserImpl implements OperationParser {

    private Lexer lexer;

    public OperationParserImpl(Lexer lexer) {
        this.lexer = lexer;
    }

    @Override
    public Operation parse(String line) throws ParseException {
        // tokenize input - just simple splitting of
        // the line into an array of string tokens
        String[] iTokens = lexer.getTokens(line);
        // extract operation name
        String operationName = iTokens[0];

        Operation operation = OperationFactory.getOperationByName(operationName);
        Grammar grammar = GrammarFactory.getOperationGrammar(operation);
        Rule[] rules = grammar.getRules();

        for (Rule rule : rules) {
            Token[] rTokens = rule.getTokens();
            // check if rule tokens matched to input token
            // values
            if (match(rTokens, iTokens)) {
                // prepare an instance of the operation by
                // setting its fields
                Reflector.set(operation, rTokens, iTokens);
            }
        }
        return operation;
    }

    /**
     * Match rule tokens to input tokens
     *
     * @param rTokens rule tokens
     * @param iTokens input tokens
     * @return true if matched
     */
    boolean match(Token[] rTokens, String[] iTokens) {
        // the size of rule tokens doesn't fit to
        // the size of user input
        if (rTokens.length != iTokens.length) {
            return false; // skip
        }
        for (int i = 0; i < rTokens.length; i++) {
            // all matching logic is encapsulated in
            // the token
            if (rTokens[i].match(iTokens[i])) {
                return false;
            }
        }
        return true;
    }
}
