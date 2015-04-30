package ua.krasnyanskiy.jrsh.evaluation;

import lombok.NonNull;
import ua.krasnyanskiy.jrsh.operation.parameter.OperationParser;

/**
 * @author Alexander Krasnyanskiy
 * @since 1.0
 */
public interface EvaluationStrategy {

    void eval(@NonNull String[] args) throws Exception;

    void setOperationParser(OperationParser parser);

}
