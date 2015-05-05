package ua.krasnyanskiy.jrsh.operation.parser;

import lombok.NonNull;
import ua.krasnyanskiy.jrsh.operation.Operation;
import ua.krasnyanskiy.jrsh.operation.grammar.token.TokenPreconditions;
import ua.krasnyanskiy.jrsh.operation.parameter.OperationParameters;

public class OperationParserFacade implements OperationParser {

    private OperationParser formattedLoginTokenParser = new PlainLoginOperationParser();
    private OperationParser ll1Parser = new LL1OperationParser();

    @Override
    public Operation<? extends OperationParameters> parse(@NonNull String line) {
        Operation<? extends OperationParameters> operation;

        operation = TokenPreconditions.isLoginToken(line) ? formattedLoginTokenParser.parse(line) : ll1Parser.parse(line);

        return operation;
    }
}