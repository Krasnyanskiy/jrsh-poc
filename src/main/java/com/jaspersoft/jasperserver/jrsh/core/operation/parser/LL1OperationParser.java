package com.jaspersoft.jasperserver.jrsh.core.operation.parser;

import com.jaspersoft.jasperserver.jrsh.core.operation.*;
import com.jaspersoft.jasperserver.jrsh.core.operation.grammar.Grammar;
import com.jaspersoft.jasperserver.jrsh.core.operation.grammar.Lexer;
import com.jaspersoft.jasperserver.jrsh.core.operation.grammar.Lexer.DefaultLexer;
import com.jaspersoft.jasperserver.jrsh.core.operation.grammar.Rule;
import com.jaspersoft.jasperserver.jrsh.core.operation.grammar.token.Token;
import lombok.NonNull;
import lombok.Setter;

import java.util.List;

/**
 * @author Alexander Krasnyanskiy
 * @version 1.0
 */
public class LL1OperationParser implements OperationParser {

    @Setter private Lexer lexer;

    public LL1OperationParser() {
        lexer = new DefaultLexer();
    }

    @Override @NonNull public Operation parse(String line) throws OperationParseException {

        List<String> inputTokens = lexer.getTokens(line);
        String operationName = inputTokens.get(0);

        Operation operation = OperationFactory.getOperationByName(operationName);
        Grammar grammar = OperationGrammarFactory.getOperationGrammar(operation);
        List<Rule> grammarRules = grammar.getRules();

        for (Rule rule : grammarRules) {
            List<Token> ruleTokens = rule.getTokens();
            if (match(ruleTokens, inputTokens)) {
                Reflector.set(operation, ruleTokens, inputTokens);
            }
        }
        Postconditions.checkOperation(operation);
        return operation;
    }

    /**
     * Checks if rule tokens matched to the input tokens.
     *
     * @param ruleTokens  tokens
     * @param inputTokens tokens
     * @return true if matched
     */
    protected boolean match(List<Token> ruleTokens, List<String> inputTokens) {
        if (ruleTokens.size() != inputTokens.size()) {
            return false;
        }
        for (int i = 0; i < ruleTokens.size(); i++) {
            if (ruleTokens.get(i).match(inputTokens.get(i))) {
                return false;
            }
        }
        return true;
    }
}
