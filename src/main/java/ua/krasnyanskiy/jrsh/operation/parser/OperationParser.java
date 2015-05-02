package ua.krasnyanskiy.jrsh.operation.parser;

import ua.krasnyanskiy.jrsh.operation.Operation;

/**
 * @author Alexander Krasnyanskiy
 * @since 1.0
 */
public interface OperationParser {

    Operation parse(String[] tokens);

    Operation parse(String tokens);
}
