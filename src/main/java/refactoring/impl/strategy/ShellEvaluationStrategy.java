package refactoring.impl.strategy;

import jline.console.ConsoleReader;
import lombok.val;
import refactoring.EvaluationStrategy;
import refactoring.OperationParser;
import refactoring.impl.CompleterFactory;
import refactoring.impl.StringJoiner;
import ua.krasnyanskiy.jrsh.common.ConsoleBuilder;
import ua.krasnyanskiy.jrsh.common.SessionFactory;
import ua.krasnyanskiy.jrsh.completion.JrshCompletionHandler;

/**
 * @author Alexander Krasnyanskiy
 * @version 1.0
 */
public class ShellEvaluationStrategy implements EvaluationStrategy {

    private final ConsoleReader console;
    private final OperationParser parser;
    private final String[] args;

    public ShellEvaluationStrategy(String[] args, OperationParser parser, ConsoleBuilder builder, CompleterFactory factory) {
        this.args = args;
        this.parser = parser;
        this.console = builder.withPrompt("$> ")
                .withHandler(new JrshCompletionHandler())
                .withCompleter(factory.getAggregatedCompleter())
                .build();
    }

    @Override
    public void eval() throws Exception {
        try {
            val line = StringJoiner.join(args);
            val operation = parser.parse(line);
            val session = SessionFactory.getSharedSession();
            val res = operation.eval(session);

            console.println(res.getMessage());
        } catch (Exception err) {
            console.println(err.getMessage());
        }
    }
}
