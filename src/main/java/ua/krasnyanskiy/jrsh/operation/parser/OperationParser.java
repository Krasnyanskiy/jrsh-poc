package ua.krasnyanskiy.jrsh.operation.parser;

import ua.krasnyanskiy.jrsh.operation.Operation;
import ua.krasnyanskiy.jrsh.operation.parameter.OperationParameters;

/**
 * @author Alexander Krasnyanskiy
 * @since 1.0
 */
public interface OperationParser {

    Operation<? extends OperationParameters> parse(String line);

}
