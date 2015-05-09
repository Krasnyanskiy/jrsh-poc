package ua.krasnyanskiy.jrsh.operation.parser;

import lombok.NonNull;
import lombok.SneakyThrows;
import ua.krasnyanskiy.jrsh.common.NoSuchOperationException;
import ua.krasnyanskiy.jrsh.common.ParseOperationException;
import ua.krasnyanskiy.jrsh.common.ParseParametersException;
import ua.krasnyanskiy.jrsh.common.Reflection;
import ua.krasnyanskiy.jrsh.operation.Operation;
import ua.krasnyanskiy.jrsh.operation.OperationFactory;
import ua.krasnyanskiy.jrsh.operation.grammar.Grammar;
import ua.krasnyanskiy.jrsh.operation.grammar.Rule;
import ua.krasnyanskiy.jrsh.operation.grammar.token.Token;
import ua.krasnyanskiy.jrsh.operation.parameter.AbstractOperationParameters;

import java.util.Collection;
import java.util.List;

import static java.lang.String.format;

public class LL1OperationParser implements OperationParser {

    private static final String ERROR_MSG = "Cannot parse parameters for (\u001B[1m%s\u001B[0m)";

    @Override
    @SuppressWarnings("unchecked")
    public Operation<? extends AbstractOperationParameters> parse(String line) throws ParseOperationException {
        String[] tokens = line.split("\\s+"); // mini lexer
        String operationName = tokens[0];
        Operation<AbstractOperationParameters> operation = (Operation<AbstractOperationParameters>)
                OperationFactory.getOperation(operationName);

        if (operation == null) {
            throw new NoSuchOperationException(operationName);
        }

        AbstractOperationParameters parameters = getOperationParameters(operation, tokens);
        operation.setOperationParameters(parameters);
        return operation;
    }

    @SneakyThrows
    protected AbstractOperationParameters getOperationParameters(@NonNull Operation operation,
                                                                 @NonNull String[] tokenValues) {
        AbstractOperationParameters parameters = null;
        Grammar grammar = operation.getGrammar();
        Collection<Rule> rules = grammar.getRules();

        for (Rule rule : rules) {
            List<Token> ruleTokens = rule.getTokens();
            boolean isMatchingRule = true;
            if (tokenValues.length == ruleTokens.size()) {
                for (int i = 0; i < ruleTokens.size(); i++) {
                    if (!ruleTokens.get(i).match(tokenValues[i])) {
                        isMatchingRule = false;
                        break;
                    }
                }
                // matched
                if (isMatchingRule) {
                    if (parameters == null) {
                        parameters = (AbstractOperationParameters) operation.getParametersType().newInstance();
                    }
                    Reflection.setParametersValues(parameters, ruleTokens, tokenValues);
                }
            }
        }
        if (parameters == null) {
            throw new ParseParametersException(format(ERROR_MSG, tokenValues[0]));
        }
        return parameters;
    }
}
