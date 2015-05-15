package refactoring.impl.strategy;

import jline.console.ConsoleReader;
import lombok.val;
import refactoring.EvaluationStrategy;
import refactoring.OperationParser;
import refactoring.impl.StringJoiner;
import ua.krasnyanskiy.jrsh.common.ConsoleBuilder;
import ua.krasnyanskiy.jrsh.common.SessionFactory;

/**
 * @author Alexander Krasnyanskiy
 * @version 1.0
 */
public class ToolEvaluationStrategy implements EvaluationStrategy {

    private final String[] args;
    private final OperationParser parser;
    private final ConsoleReader console;

    public ToolEvaluationStrategy(String[] args, OperationParser parser, ConsoleBuilder builder) {
        this.args = args;
        this.parser = parser;
        this.console = builder.build();
    }

    @Override
    public void eval() throws Exception {
        try {
            String line = StringJoiner.join(args);

            val session = SessionFactory.getSharedSession();
            val operation = parser.parse(line);
            val res = operation.eval(session);

            console.println(res.getMessage());
        } catch (Exception err) {
            console.println(err.getMessage());
        }
    }
}
