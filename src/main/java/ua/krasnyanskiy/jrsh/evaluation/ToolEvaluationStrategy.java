package ua.krasnyanskiy.jrsh.evaluation;

import jline.console.ConsoleReader;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import ua.krasnyanskiy.jrsh.operation.EvaluationResult;
import ua.krasnyanskiy.jrsh.operation.EvaluationResult.ResultCode;
import ua.krasnyanskiy.jrsh.operation.Operation;
import ua.krasnyanskiy.jrsh.operation.grammar.token.TokenPreconditions;
import ua.krasnyanskiy.jrsh.operation.parameter.OperationParameters;
import ua.krasnyanskiy.jrsh.operation.parser.OperationParser;

import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.Callable;

import static java.lang.System.exit;
import static ua.krasnyanskiy.jrsh.common.Separator.WHITE_SPACE;

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
    }

    public void eval(@NonNull String[] tokens) throws IOException {
        try {
            String operation;
            if (tokens.length == 0) {
                console.println("use \u001B[1mhelp\u001B[0m <operation>");
                console.flush();
                exit(1);
            }
            if (tokens.length > 1) {
                if (TokenPreconditions.isLoginToken(tokens[0])) {
                    String login = "login".concat(WHITE_SPACE).concat(tokens[0]);
                    EvaluationResult result = parser.parse(login).eval().call();

                    if (result.getCode() == ResultCode.FAILED) {
                        console.println(result.getMessage());
                        return;
                    }
                    String[] tkns = Arrays.copyOfRange(tokens, 1, tokens.length);
                    operation = StringUtils.join(tkns, WHITE_SPACE);
                } else {
                    operation = StringUtils.join(tokens, WHITE_SPACE);
                }
            } else {
                operation = tokens[0]; // only one operation w/o any parameter
            }

            Operation<? extends OperationParameters> op = parser.parse(operation);
            Callable<EvaluationResult> task = op.eval();
            EvaluationResult result = task.call();
            console.println(result.getMessage());
        } catch (Exception err) {
            console.print("error: " + err.getMessage());
        } finally {
            console.flush();
        }
    }

    @Override
    public void setOperationParser(OperationParser parser) {
        this.parser = parser;
    }
}
