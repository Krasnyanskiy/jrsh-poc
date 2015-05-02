package ua.krasnyanskiy.jrsh.evaluation;

import jline.console.ConsoleReader;
import jline.console.completer.AggregateCompleter;
import jline.console.completer.Completer;
import lombok.NonNull;
import ua.krasnyanskiy.jrsh.common.CandidatesCustomCompletionHandler;
import ua.krasnyanskiy.jrsh.operation.Operation;
import ua.krasnyanskiy.jrsh.operation.OperationFactory;
import ua.krasnyanskiy.jrsh.operation.parser.OperationParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * @author Alexander Krasnyanskiy
 * @since 1.0
 */
public class ShellEvaluationStrategy implements EvaluationStrategy {

    private ConsoleReader console;
    private OperationParser parser;

    public ShellEvaluationStrategy() throws IOException {
        this.console = createConsole();
    }

    @Override
    public void eval(@NonNull String[] tokens) throws Exception {

        // make a session from the tokens;

        while (true) {
            String input = console.readLine();
            if (input.isEmpty()) {
                console.print("");
                continue;
            }

            Operation op = parser.parse(input);
            Callable job = op.execute();
            String result = job.call().toString();

            console.println(result);

        }
    }

    /**
     * Add parser. For more details please see
     * {@link EvaluationStrategy#setOperationParser(OperationParser)}
     *
     * @param parser operation parser
     */
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
    protected ConsoleReader createConsole() throws IOException {
        ConsoleReader console = new ConsoleReader();
        console.setPrompt("\u001B[1m>>> \u001B[0m");
        console.setCompletionHandler(new CandidatesCustomCompletionHandler());
        List<Completer> list = new ArrayList<>();

        for (Operation o : OperationFactory.getOperations()) {
            Completer completer = o.getGrammar().getCompleter();
            list.add(completer);
        }
        console.addCompleter(new AggregateCompleter(list));
        return console;
    }
}
