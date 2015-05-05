package ua.krasnyanskiy.jrsh.operation.parser;

import lombok.NonNull;
import lombok.SneakyThrows;
import ua.krasnyanskiy.jrsh.common.NoSuchOperationException;
import ua.krasnyanskiy.jrsh.common.ReflectionUtil;
import ua.krasnyanskiy.jrsh.operation.Operation;
import ua.krasnyanskiy.jrsh.operation.OperationFactory;
import ua.krasnyanskiy.jrsh.operation.grammar.Grammar;
import ua.krasnyanskiy.jrsh.operation.grammar.Rule;
import ua.krasnyanskiy.jrsh.operation.grammar.token.Token;
import ua.krasnyanskiy.jrsh.operation.parameter.OperationParameters;

import java.util.Collection;
import java.util.List;

import static java.lang.String.format;

public class LL1OperationParser implements OperationParser {

    private static final String PARSE_ERROR = "Cannot parse parameters for (\u001B[1m%s\u001B[0m)";

    /**
     * Parses operation.
     *
     * @param line pure operation with parameters
     * @return configured operation
     */
    @Override
    public Operation<? extends OperationParameters> parse(String line) {

        String[] tokens = line.split("\\s+"); // tokenize

        String operationName = tokens[0];
        Operation<OperationParameters> operation = OperationFactory.getOperation(operationName);

        if (operation == null) {
            throw new NoSuchOperationException();
        }

        OperationParameters parameters = getParameters(operation, tokens);
        operation.setOperationParameters(parameters);
        return operation;
    }

    @SneakyThrows
    protected OperationParameters getParameters(@NonNull Operation operation, @NonNull String[] tokens) {

        OperationParameters parameters = null; // empty
        Grammar grammar = operation.getGrammar();
        Collection<Rule> rules = grammar.getRules();

        for (Rule rule : rules) {
            List<Token> tokens_ = rule.getTokens();
            boolean isMatchingRule = true;
            if (tokens.length == tokens_.size()) {
                for (int i = 0; i < tokens_.size(); i++) {
                    if (!tokens_.get(i).match(tokens[i])) {
                        isMatchingRule = false;
                    }
                }
                if (isMatchingRule) {
                    for (int i = 0; i < tokens_.size(); i++) {
                        Token token = tokens_.get(i);
                        if (token.isValueToken()) {
                            continue;
                        }
                        if (parameters == null) {
                            parameters = (OperationParameters) operation.getParametersType().newInstance();
                        }
                        String val;
                        if (i + 1 < tokens.length) {
                            if (tokens_.get(i + 1).isValueToken()) {
                                val = tokens[i + 1];
                            } else {
                                val = tokens[i];
                            }
                        } else {
                            val = tokens[i];
                        }
                        ReflectionUtil.setParameterValue(parameters, token.getName(), val);
                    }
                }
            }
        }
        if (parameters == null) {
            throw new RuntimeException(format(PARSE_ERROR, tokens[0]));
        }

        return parameters;
    }
}
