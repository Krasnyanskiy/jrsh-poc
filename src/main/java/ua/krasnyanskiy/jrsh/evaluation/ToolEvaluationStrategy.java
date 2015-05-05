package ua.krasnyanskiy.jrsh.evaluation;

import jline.console.ConsoleReader;
import lombok.NonNull;
import lombok.SneakyThrows;
import ua.krasnyanskiy.jrsh.completion.JrshCompletionHandler;
import ua.krasnyanskiy.jrsh.operation.parser.OperationParser;

/**
 * @author Alexander Krasnyanskiy
 * @since 1.0
 */
@SuppressWarnings("unchecked")
public class ToolEvaluationStrategy implements EvaluationStrategy {

    private ConsoleReader console;
    private OperationParser parser;

    @SneakyThrows
    public ToolEvaluationStrategy() {
        this.console = new ConsoleReader();
        this.console.setCompletionHandler(new JrshCompletionHandler());
    }

    @SneakyThrows
    public void eval(@NonNull String[] tokens) {
        if (tokens.length == 0) {
            console.println("Hello, Sir! Need help? Better call Saul!");
        } else {
            console.println("This version doesn't support that yet. Hasta la vista!");
        }
        console.flush();
    }

    @Override
    public void setOperationParser(OperationParser parser) {
        this.parser = parser;
    }
}
