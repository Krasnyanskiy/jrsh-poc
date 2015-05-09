package ua.krasnyanskiy.jrsh.operation.parser;

import ua.krasnyanskiy.jrsh.exception.ParseOperationException;
import ua.krasnyanskiy.jrsh.operation.Operation;
import ua.krasnyanskiy.jrsh.operation.parameter.AbstractOperationParameters;

/**
 * @author Alexander Krasnyanskiy
 * @since 1.0
 */
public interface OperationParser {

    Operation<? extends AbstractOperationParameters> parse(String line) throws ParseOperationException;

}
