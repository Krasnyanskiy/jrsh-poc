package com.jaspersoft.jasperserver.jrsh.core.operation.parser;

import com.jaspersoft.jasperserver.jrsh.core.operation.*;
import com.jaspersoft.jasperserver.jrsh.core.operation.grammar.Grammar;
import com.jaspersoft.jasperserver.jrsh.core.operation.grammar.Lexer;
import com.jaspersoft.jasperserver.jrsh.core.operation.grammar.Lexer.DefaultLexer;
import com.jaspersoft.jasperserver.jrsh.core.operation.grammar.Rule;
import com.jaspersoft.jasperserver.jrsh.core.operation.grammar.token.Token;
import com.jaspersoft.jasperserver.jrsh.core.operation.parser.exception.OperationParseException;
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

    /**
     * Parses a line to operation.
     *
     * @param line input
     * @return operation
     * @throws OperationParseException
     */
    @NonNull @Override public Operation parse(String line) throws OperationParseException {

        List<String> inputTokens = lexer.getTokens(line);
        String operationName = inputTokens.get(0);

        Operation operation = OperationFactory.getOperationByName(operationName);
        Conditions.checkOperation(operation);

        Grammar grammar = GrammarMetadataParser.parseGrammar(operation);
        List<Rule> grammarRules = grammar.getRules();

        boolean matchedRuleExist = false;

        for (Rule rule : grammarRules) {
            List<Token> ruleTokens = rule.getTokens();
            if (match(ruleTokens, inputTokens)) {
                OperationReflector.set(operation, ruleTokens, inputTokens);
                matchedRuleExist = true;
            }
        }
        Conditions.checkMatchedRules(matchedRuleExist);
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
            if (!ruleTokens.get(i).match(inputTokens.get(i))) {
                return false;
            }
        }
        return true;
    }
}
