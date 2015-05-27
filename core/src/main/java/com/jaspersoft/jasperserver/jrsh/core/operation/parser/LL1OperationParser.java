package com.jaspersoft.jasperserver.jrsh.core.operation.parser;

import com.jaspersoft.jasperserver.jrsh.core.operation.Operation;
import com.jaspersoft.jasperserver.jrsh.core.operation.OperationFactory;
import com.jaspersoft.jasperserver.jrsh.core.operation.OperationReflector;
import com.jaspersoft.jasperserver.jrsh.core.operation.grammar.Grammar;
import com.jaspersoft.jasperserver.jrsh.core.operation.grammar.Lexer;
import com.jaspersoft.jasperserver.jrsh.core.operation.grammar.Lexer.DefaultLexer;
import com.jaspersoft.jasperserver.jrsh.core.operation.grammar.Rule;
import com.jaspersoft.jasperserver.jrsh.core.operation.grammar.token.Token;
import com.jaspersoft.jasperserver.jrsh.core.operation.parser.exception.OperationParseException;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

import java.util.List;

@Log4j
public class LL1OperationParser implements OperationParser {

    @Setter
    private Lexer lexer;

    public LL1OperationParser() {
        lexer = new DefaultLexer();
    }

    public Operation parse(String line) throws OperationParseException {
        List<String> inputTokens = lexer.getTokens(line);
        String operationName = inputTokens.get(0);

        Operation operation = OperationFactory.createOperationByName(operationName);
        Conditions.checkOperation(operation);
        Grammar grammar = GrammarMetadataParser.parse(operation);
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
