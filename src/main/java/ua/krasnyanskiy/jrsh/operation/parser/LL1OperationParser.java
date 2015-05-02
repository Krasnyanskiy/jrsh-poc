package ua.krasnyanskiy.jrsh.operation.parser;

import lombok.NonNull;
import lombok.SneakyThrows;
import ua.krasnyanskiy.jrsh.operation.Operation;
import ua.krasnyanskiy.jrsh.operation.OperationFactory;
import ua.krasnyanskiy.jrsh.operation.grammar.Grammar;
import ua.krasnyanskiy.jrsh.operation.grammar.Rule;
import ua.krasnyanskiy.jrsh.operation.grammar.token.Token;
import ua.krasnyanskiy.jrsh.operation.parameter.OperationParameters;

import java.util.Collection;
import java.util.List;

public class LL1OperationParser implements OperationParser {

    @Override
    @SuppressWarnings("unchecked")
    public Operation parse(@NonNull String[] tokens) {
        String operationNameToken = tokens[0];
        Operation operation = OperationFactory.getOperation(operationNameToken);
        if (operation == null) {
            throw new RuntimeException("Нет такой операции");
        }
        OperationParameters parameters = getParameters(operation, tokens);
        operation.setOperationParameters(parameters); // TODO: проверить типы
        return operation;
    }

    @Override
    public Operation parse(@NonNull String tokens) {
        return parse(tokens.split("\\s+"));
    }

    @SneakyThrows
    protected OperationParameters getParameters(@NonNull Operation operation, @NonNull String[] tokens) {

        // TODO: implement me!

        OperationParameters parameters = (OperationParameters) operation.getParametersType().newInstance();
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
                        //params.put(token.getName(), args[i]);
                        // TODO: !!!
                    }
                }
            }
        }

        return parameters;
    }
}