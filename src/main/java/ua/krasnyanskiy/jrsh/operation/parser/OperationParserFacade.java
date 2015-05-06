package ua.krasnyanskiy.jrsh.operation.parser;

import lombok.NonNull;
import ua.krasnyanskiy.jrsh.operation.Operation;
import ua.krasnyanskiy.jrsh.operation.parameter.OperationParameters;

public class OperationParserFacade implements OperationParser {

    private OperationParser ll1Parser = new LL1OperationParser();

    @Override
    public Operation<? extends OperationParameters> parse(@NonNull String line) {
        Operation<? extends OperationParameters> operation;
        operation = ll1Parser.parse(line);
        return operation;
    }
}