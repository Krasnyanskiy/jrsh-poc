package ua.krasnyanskiy.jrsh.evaluation;

import ua.krasnyanskiy.jrsh.operation.parser.OperationParser;

/**
 * All plug-in execution strategy classes should implement
 * this interface.
 *
 * @author Alexander Krasnyanskiy
 * @since 1.0
 */
public interface EvaluationStrategy {

    /**
     * Evaluates an expression which is corresponding to the
     * concrete JRSH operation.
     *
     * @param tokens tokens
     * @throws Exception
     */
    void eval(String[] tokens) throws Exception;

    /**
     * Add specific operation parser to strategy. The parser
     * helps to parse an expression into the operation.
     *
     * @param parser operation parser
     */
    void setOperationParser(OperationParser parser);

}
