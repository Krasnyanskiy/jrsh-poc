package ua.krasnyanskiy.jrsh.operation;

import ua.krasnyanskiy.jrsh.operation.grammar.Grammar;
import ua.krasnyanskiy.jrsh.operation.parameter.AbstractOperationParameters;

/**
 * Base interface for all operations in the application.
 *
 * @author Alexander Krasnyanskiy
 * @since 1.0
 */
public interface Operation<P extends AbstractOperationParameters> {

    EvaluationResult eval();

    Grammar getGrammar();

    String getDescription();

    Class<P> getParametersType();

    void setOperationParameters(P parameters);

}
