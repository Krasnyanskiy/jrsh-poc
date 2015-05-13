package ua.krasnyanskiy.jrsh.evaluation;

import ua.krasnyanskiy.jrsh.operation.parser.OperationParametersParser;

/**
 * All plug-in execution strategy classes should implement
 * this interface. Strategy is responsible for operation
 * evaluation and handling the result.
 *
 * @author Alexander Krasnyanskiy
 * @since 1.0
 */
public interface EvaluationStrategy {

    void eval(String[] args) throws Exception;

    void setOperationParser(OperationParametersParser parser);

}
