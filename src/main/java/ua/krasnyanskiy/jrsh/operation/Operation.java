package ua.krasnyanskiy.jrsh.operation;

import ua.krasnyanskiy.jrsh.operation.parser.OperationParameters;

/**
 * Base interface for all operations in the application.
 *
 * @author Alexander Krasnyanskiy
 * @since 1.0
 */
public interface Operation<P extends OperationParameters> {

    EvaluationResult eval(P parameters);

    //Grammar getGrammar();

    //String getDescription();

    //Class<P> getParametersType();

    //void setOperationParameters(P parameters);

}
