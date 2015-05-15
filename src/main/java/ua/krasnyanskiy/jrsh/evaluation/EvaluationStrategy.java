package ua.krasnyanskiy.jrsh.evaluation;

import ua.krasnyanskiy.jrsh.exception.ParseOperationParametersException;

import java.io.IOException;

/**
 * All plug-in execution strategy classes should implement
 * this interface. Strategy is responsible for operation
 * evaluation and handling the result.
 *
 * @author Alexander Krasnyanskiy
 * @since 1.0
 */
public interface EvaluationStrategy {

    /**
     * Evaluates an expression.
     */
    void eval(String[] appArgs) throws IOException, ParseOperationParametersException;

}
