package ua.krasnyanskiy.jrsh.evaluation;

import lombok.NonNull;
import ua.krasnyanskiy.jrsh.common.ConsoleBuilder;

import java.io.IOException;

/**
 * @author Alexander Krasnyanskiy
 * @since 1.0
 */
public class ShellEvaluationStrategy extends AbstractEvaluationStrategy {

    public ShellEvaluationStrategy() {
        super.console = new ConsoleBuilder().build();
    }

    @Override
    public void eval(@NonNull String[] appArgs) throws IOException {
        String line = "login ".concat(appArgs[0]);
        console.println(line);
        console.flush();
    }
}
