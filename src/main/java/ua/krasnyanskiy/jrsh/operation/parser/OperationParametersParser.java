package ua.krasnyanskiy.jrsh.operation.parser;

import ua.krasnyanskiy.jrsh.exception.ParseOperationParametersException;
import ua.krasnyanskiy.jrsh.operation.parameter.OperationParameters;

/**
 * @author Alexander Krasnyanskiy
 * @since 1.0
 */
public interface OperationParametersParser {
    /**
     * Parse operation parameters.
     *
     * @param line user input
     * @return operation parameters
     * @throws ParseOperationParametersException
     */
    OperationParameters parseParameters(String line) throws ParseOperationParametersException;
}