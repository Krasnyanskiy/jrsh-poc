package ua.krasnyanskiy.jrsh.evaluation;

import jline.console.ConsoleReader;
import jline.console.completer.Completer;
import lombok.NonNull;
import ua.krasnyanskiy.jrsh.exception.ParseOperationException;
import ua.krasnyanskiy.jrsh.operation.EvaluationResult;
import ua.krasnyanskiy.jrsh.operation.EvaluationResult.ResultCode;
import ua.krasnyanskiy.jrsh.operation.Operation;
import ua.krasnyanskiy.jrsh.operation.OperationFactory;
import ua.krasnyanskiy.jrsh.operation.parameter.AbstractOperationParameters;
import ua.krasnyanskiy.jrsh.operation.parser.OperationParametersParser;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.LogManager;

public abstract class AbstractEvaluationStrategy implements EvaluationStrategy {

    protected ConsoleReader console;
    protected OperationParametersParser parser;

    public AbstractEvaluationStrategy() {
        LogManager.getLogManager().reset(); // reset Jersey Client logger
    }

    /**
     * Parse input, eval expression and return evaluation result
     *
     * @param line input
     */
    protected EvaluationResult parseAndEvaluate(@NonNull String line) {
        EvaluationResult res;
        try {
            Operation<? extends AbstractOperationParameters> operation = parser.parse(line);
            res = operation.eval();
        } catch (ParseOperationException err) {
            res = new EvaluationResult(err.getMessage(), ResultCode.FAILED, null);
        }
        return res;
    }

    @Override
    public void setOperationParser(OperationParametersParser parser) {
        this.parser = parser;
    }

    protected List<Completer> getCompleters() {
        List<Completer> completers = new ArrayList<>();
        for (Operation op : OperationFactory.getOperations()) {
            Completer cmt = op.getGrammar().getCompleter();
            completers.add(cmt);
        }
        return completers;
    }
}
