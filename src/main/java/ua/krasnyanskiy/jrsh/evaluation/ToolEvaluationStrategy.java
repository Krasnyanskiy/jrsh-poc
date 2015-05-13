package ua.krasnyanskiy.jrsh.evaluation;

import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;
import ua.krasnyanskiy.jrsh.common.ConsoleBuilder;
import ua.krasnyanskiy.jrsh.operation.EvaluationResult;
import ua.krasnyanskiy.jrsh.operation.EvaluationResult.ResultCode;
import ua.krasnyanskiy.jrsh.operation.OperationFactory;
import ua.krasnyanskiy.jrsh.operation.grammar.token.TokenPreconditions;

import java.io.IOException;
import java.util.Arrays;

import static java.lang.System.exit;
import static ua.krasnyanskiy.jrsh.common.Separator.WHITE_SPACE;

/**
 * @author Alexander Krasnyanskiy
 * @since 1.0
 */
public class ToolEvaluationStrategy extends AbstractEvaluationStrategy {

    public ToolEvaluationStrategy() {
        super.console = new ConsoleBuilder().build();
    }

    public void eval(@NonNull String[] args) throws IOException {
        EvaluationResult res;
        String operation;

        switch (args.length) {
            case 0:
                res = OperationFactory.getOperation("help").eval();
                break;
            case 1:
                res = parseAndEvaluate(args[0]);
                break;
            default:
                operation = args[0];
                if (TokenPreconditions.isConnectionStringToken(operation)) {
                    operation = "login ".concat(operation);
                    res = parseAndEvaluate(operation);
                    if (res.getCode() == ResultCode.FAILED) {
                        console.println(res.getMessage());
                        console.flush();
                        exit(1);
                    }
                    String[] __args = Arrays.copyOfRange(args, 1, args.length);
                    operation = StringUtils.join(__args, WHITE_SPACE);
                } else {
                    operation = StringUtils.join(args, WHITE_SPACE);
                }
                res = parseAndEvaluate(operation);
        }

        console.println(res.getMessage());
        console.flush();
    }
}
