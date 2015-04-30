package ua.krasnyanskiy.jrsh.evaluation;

import jline.console.ConsoleReader;
import lombok.NonNull;
import lombok.SneakyThrows;
import ua.krasnyanskiy.jrsh.common.CandidatesCustomCompletionHandler;
import ua.krasnyanskiy.jrsh.operation.parameter.OperationParser;

/**
 * @author Alexander Krasnyanskiy
 * @since 1.0
 */
public class ToolEvaluationStrategy implements EvaluationStrategy {

    private ConsoleReader console;

    @SneakyThrows
    public ToolEvaluationStrategy() {
        this.console = new ConsoleReader();
        this.console.setCompletionHandler(new CandidatesCustomCompletionHandler());
    }

    @SneakyThrows
    public void eval(@NonNull String[] args) {
        if (args.length == 0) {
            console.println("Hello, Sir! Need help? Better call Saul!");
        } else {
            console.println("This version doesn't support that yet. Hasta la vista!");
        }
        console.flush();
    }

    @Override
    public void setOperationParser(OperationParser parser) {

    }
}