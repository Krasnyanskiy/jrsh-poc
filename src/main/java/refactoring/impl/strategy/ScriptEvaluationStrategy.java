package refactoring.impl.strategy;

import com.google.common.collect.Lists;
import jline.console.ConsoleReader;
import lombok.val;
import refactoring.EvaluationStrategy;
import refactoring.OperationParser;
import ua.krasnyanskiy.jrsh.common.ConsoleBuilder;
import ua.krasnyanskiy.jrsh.common.SessionFactory;

/**
 * @author Alexander Krasnyanskiy
 * @version 1.0
 */
public class ScriptEvaluationStrategy implements EvaluationStrategy {

    private final String[] args;
    private final OperationParser parser;
    private final ConsoleReader console;

    public ScriptEvaluationStrategy(String[] args, OperationParser parser, ConsoleBuilder builder) {
        this.args = args;
        this.parser = parser;
        this.console = builder.build();
    }

    @Override
    public void eval() throws Exception {

        // read file line by line
        val lines = Lists.newArrayList(args);

        try {
            for (String line : lines) {
                val operation = parser.parse(line);
                val session = SessionFactory.getSharedSession();
                val res = operation.eval(session);
                console.println(res.getMessage());
            }
        } catch (Exception err) {
            console.println(err.getMessage());
        }

    }
}
