package ua.krasnyanskiy.jrsh.operation.parameter;

import ua.krasnyanskiy.jrsh.operation.Operation;
import ua.krasnyanskiy.jrsh.operation.OperationFactory;

/**
 * Default operation parser.
 */
public class OperationParser {

    /**
     * Return an operation instance.
     *
     * @param operationName operation name
     * @return operation
     */
    public Operation parseOperation(String operationName) {
        return OperationFactory.getOperation(operationName);
    }

    /**
     * Converts an array of tokens to the parameters entity.
     *
     * @param op     tokens operation
     * @param tokens parameters
     * @return entity
     */
    public OperationParameters parseParameters(Operation op, String[] tokens) {
        return null;
    }

}
