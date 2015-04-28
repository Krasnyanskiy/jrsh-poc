package ua.krasnyanskiy.jrsh.evaluation.strategy;

import jline.console.ConsoleReader;
import jline.console.completer.AggregateCompleter;
import jline.console.completer.Completer;
import lombok.NonNull;
import lombok.SneakyThrows;
import ua.krasnyanskiy.jrsh.common.CandidatesCustomCompletionHandler;
import ua.krasnyanskiy.jrsh.operation.Operation;
import ua.krasnyanskiy.jrsh.operation.Parameters;

import java.util.concurrent.Callable;

import static java.lang.System.exit;
import static ua.krasnyanskiy.jrsh.operation.OperationFactory.getOperation;

/**
 * @author Alexander Krasnyanskiy
 * @since 1.0
 */
public class ShellEvaluationStrategy implements EvaluationStrategy {

    private ConsoleReader console;

    @SneakyThrows
    public ShellEvaluationStrategy() {
        this.console = new ConsoleReader();
        this.console.setPrompt("\u001B[1m>>> \u001B[0m");
        this.console.setCompletionHandler(new CandidatesCustomCompletionHandler());

        Completer exportCompleter = getOperation("export").getGrammar().getCompleter();
        this.console.addCompleter(new AggregateCompleter(exportCompleter));
    }

    @Override
    @SneakyThrows
    public void eval(@NonNull String... args) {

        Operation login = getOperation("login");
        login.setConsole(console);
        Parameters parameters = login.getParameters(args);
        Callable<Boolean> callable = login.perform(parameters);

        boolean connected = callable.call();
        if (!connected) exit(1);

        while (true) {
            try {
                String line = console.readLine();
                if (line.isEmpty()) {
                    console.print("");
                    continue;
                }

                String[] tokens = line.split("\\s+");
                String operationName = tokens[0];
                Operation operation = getOperation(operationName);

                if (operation == null) {
                    throw new RuntimeException(String.format("No such operation [\u001B[1m%s\u001B[0m]", operationName));
                }

                operation.setConsole(console);
                Parameters params = operation.getParameters(tokens);
                Callable call = operation.perform(params);
                call.call();


            } catch (Exception err) {
                console.print(String.format("error: %s\n", err.getMessage()));
                console.flush();
            }
        }
    }
}
