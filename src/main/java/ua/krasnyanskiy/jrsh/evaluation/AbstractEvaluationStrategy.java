package ua.krasnyanskiy.jrsh.evaluation;

import jline.console.ConsoleReader;
import ua.krasnyanskiy.jrsh.exception.ParseOperationParametersException;
import ua.krasnyanskiy.jrsh.operation.parameter.OperationParameters;
import ua.krasnyanskiy.jrsh.operation.parser.OperationParametersParser;

import java.util.logging.LogManager;

public abstract class AbstractEvaluationStrategy implements EvaluationStrategy {

    protected OperationParametersParser parser;
    protected ConsoleReader console;

    public AbstractEvaluationStrategy() {
        /* Reset default Jersey logger */
        LogManager.getLogManager().reset();
    }

    protected OperationParameters parseParameters(String line)
        throws ParseOperationParametersException {
        return parser.parseParameters(line);
    }
}
