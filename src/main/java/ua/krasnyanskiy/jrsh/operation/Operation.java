package ua.krasnyanskiy.jrsh.operation;

import ua.krasnyanskiy.jrsh.operation.parameter.OperationParameters;

/**
 * Base interface for all operations in the application.
 *
 * @author Alexander Krasnyanskiy
 * @since 1.0
 */
public interface Operation<P extends OperationParameters> {
    /**
     * Evaluates operation with given parameters and return
     * operation result.
     *
     * @param parameters operation parameters
     * @return result
     */
    OperationResult eval(P parameters);

}
