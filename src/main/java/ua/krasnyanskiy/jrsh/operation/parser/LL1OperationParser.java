package ua.krasnyanskiy.jrsh.operation.parser;

import lombok.NonNull;
import lombok.SneakyThrows;
import ua.krasnyanskiy.jrsh.common.NoSuchOperationException;
import ua.krasnyanskiy.jrsh.common.ReflectionUtil;
import ua.krasnyanskiy.jrsh.common.SessionFactory;
import ua.krasnyanskiy.jrsh.operation.Operation;
import ua.krasnyanskiy.jrsh.operation.OperationFactory;
import ua.krasnyanskiy.jrsh.operation.grammar.Grammar;
import ua.krasnyanskiy.jrsh.operation.grammar.Rule;
import ua.krasnyanskiy.jrsh.operation.grammar.token.Token;
import ua.krasnyanskiy.jrsh.operation.grammar.token.TokenPreconditions;
import ua.krasnyanskiy.jrsh.operation.parameter.LoginOperationParameters;
import ua.krasnyanskiy.jrsh.operation.parameter.OperationParameters;

import java.util.Collection;
import java.util.List;

import static java.lang.String.format;

public class LL1OperationParser implements OperationParser {

    @SuppressWarnings("unchecked")
    public Operation parse(@NonNull String[] tokens) {

        // fixme
        // Special case for Login
        // get session - to prevent building of login if we use shell mode
        // (>>> superuser%superuser@localhost:8080/jasperserver-pro)
        if (TokenPreconditions.isLoginToken(tokens[0]) && SessionFactory.getSharedSession() == null) {
            Operation login = OperationFactory.getOperation("login");
            login.setOperationParameters(new LoginOperationParameters(tokens[0]));
            return login;
        }

        String operationNameToken = tokens[0];
        Operation operation = OperationFactory.getOperation(operationNameToken);

        if (operation == null) {
            throw new NoSuchOperationException();
        }

        OperationParameters parameters = getParameters(operation, tokens);
        operation.setOperationParameters(parameters); // TODO: need type check
        return operation;
    }

    public Operation parse(@NonNull String tokens) {
        return parse(tokens.split("\\s+"));
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
                if (isMatchingRule) { // Bingo!
                    for (int i = 0; i < tokens_.size(); i++) {
                        Token token = tokens_.get(i);

                        if (token.isValueToken()) {
                            // login -s localhost
                            //          ^^^^^^^^^
                            continue;
                        }
                        if (parameters == null) {
                            parameters = (OperationParameters) operation.getParametersType().newInstance();
                        }
                        // reflection contains ugly workarounds - fixme
                        String val;
                        if (i + 1 < tokens.length) {
                            if (tokens_.get(i + 1).isValueToken()) {
                                val = tokens[i+1];
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

        if (parameters == null) { // there are no proper parameters
            throw new RuntimeException(format("Cannot parse parameters for (\u001B[1m%s\u001B[0m)", tokens[0]));
        }

        return parameters;
    }
}