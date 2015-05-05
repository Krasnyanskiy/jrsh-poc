package ua.krasnyanskiy.jrsh.evaluation;

import lombok.NonNull;
import ua.krasnyanskiy.jrsh.operation.parser.OperationParser;

/**
 * @author Alexander Krasnyanskiy
 * @since 1.0
 */
public class ScriptEvaluationStrategy implements EvaluationStrategy {

    private OperationParser parser;

    @Override
    public void eval(@NonNull String[] tokens) {
        // TODO:
        // 1) read file line by line
        // 2) parse line and eval
    }

    @Override
    public void setOperationParser(OperationParser parser) {
        this.parser = parser;
    }
}
