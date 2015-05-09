package ua.krasnyanskiy.jrsh.evaluation;

import lombok.NonNull;
import ua.krasnyanskiy.jrsh.operation.EvaluationResult;
import ua.krasnyanskiy.jrsh.operation.impl.LoginOperation;

import static java.lang.System.exit;
import static ua.krasnyanskiy.jrsh.common.Separator.WHITE_SPACE;

/**
 * @author Alexander Krasnyanskiy
 * @since 1.0
 */
public class ShellEvaluationStrategy extends AbstractEvaluationStrategy {

    @Override
    public void eval(@NonNull String[] args) throws Exception {
        int loginTimes = 0;
        String line = "login".concat(WHITE_SPACE).concat(args[0]);

        while (true) {
            if (line == null) {
                line = console.readLine();
            }
            if (line.isEmpty()) {
                console.print("");
                console.flush();
            } else {
                EvaluationResult res = parseAndEvaluate(line);
                switch (res.getCode()) {
                    case SUCCESS: {
                        if (res.getContext() instanceof LoginOperation) {
                            loginTimes++;
                        }
                        console.println(res.getMessage());
                        break;
                    }
                    default: {
                        if (res.getContext() instanceof LoginOperation && loginTimes == 0) {
                            console.println(res.getMessage());
                            console.flush();
                            // say goodbye to VM
                            exit(1);
                        } else {
                            console.println(res.getMessage());
                        }
                    }
                }
                console.flush();
            }
            line = null;
        }
    }
}
