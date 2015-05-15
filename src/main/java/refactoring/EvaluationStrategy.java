package refactoring;

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
     * Evaluates the algorithm logic
     */
    void eval() throws Exception;
}
