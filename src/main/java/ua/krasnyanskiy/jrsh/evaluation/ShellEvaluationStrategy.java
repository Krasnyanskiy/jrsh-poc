package ua.krasnyanskiy.jrsh.evaluation;

import jline.console.ConsoleReader;
import jline.console.completer.AggregateCompleter;
import jline.console.completer.Completer;
import lombok.NonNull;
import ua.krasnyanskiy.jrsh.common.CandidatesCustomCompletionHandler;
import ua.krasnyanskiy.jrsh.operation.Operation;
import ua.krasnyanskiy.jrsh.operation.parameter.OperationParameters;
import ua.krasnyanskiy.jrsh.operation.parameter.OperationParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import static ua.krasnyanskiy.jrsh.operation.OperationFactory.getOperations;

/**
 * @author Alexander Krasnyanskiy
 * @since 1.0
 */
public class ShellEvaluationStrategy implements EvaluationStrategy {

    private ConsoleReader console;
    private OperationParser parser;

    public ShellEvaluationStrategy() throws IOException {
        this.console = getConsole();
    }

    @Override
    @SuppressWarnings("unchecked")
    public void eval(@NonNull String[] args) throws Exception {
        while (true) {
            String input = console.readLine();
            if (input.isEmpty()) {
                console.print("");
                continue;
            }

            String[] tokens = input.split("\\s+");
            String operationName = tokens[0];

            Operation operation = parser.parseOperation(operationName);
            OperationParameters parameters = parser.parseParameters(operation, tokens);
            operation.setConsole(console);

            Callable c = operation.perform(parameters);
            c.call();
        }
    }

    @Override
    public void setOperationParser(OperationParser parser) {
        this.parser = parser;
    }

    /**
     * Create a new configured {@link ConsoleReader}
     *
     * @return console
     * @throws IOException
     */
    protected ConsoleReader getConsole() throws IOException {
        ConsoleReader console = new ConsoleReader();
        console.setPrompt("\u001B[1m>>> \u001B[0m");
        console.setCompletionHandler(new CandidatesCustomCompletionHandler());
        List<Completer> list = new ArrayList<>();

        for (Operation o : getOperations()) {
            Completer completer = o.getGrammar().getCompleter();
            list.add(completer);
        }
        console.addCompleter(new AggregateCompleter(list));
        return console;
    }
}
