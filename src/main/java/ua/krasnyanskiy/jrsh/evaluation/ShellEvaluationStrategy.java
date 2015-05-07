package ua.krasnyanskiy.jrsh.evaluation;

import jline.console.ConsoleReader;
import jline.console.completer.Completer;
import lombok.NonNull;
import ua.krasnyanskiy.jrsh.common.ConsoleBuilder;
import ua.krasnyanskiy.jrsh.completion.JrshCompletionHandler;
import ua.krasnyanskiy.jrsh.operation.EvaluationResult;
import ua.krasnyanskiy.jrsh.operation.EvaluationResult.ResultCode;
import ua.krasnyanskiy.jrsh.operation.Operation;
import ua.krasnyanskiy.jrsh.operation.OperationFactory;
import ua.krasnyanskiy.jrsh.operation.parameter.OperationParameters;
import ua.krasnyanskiy.jrsh.operation.parser.OperationParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.LogManager;

import static ua.krasnyanskiy.jrsh.common.Separator.WHITE_SPACE;

/**
 * @author Alexander Krasnyanskiy
 * @since 1.0
 */
public class ShellEvaluationStrategy implements EvaluationStrategy {

    private ConsoleReader console;
    private OperationParser parser;

    public ShellEvaluationStrategy() throws IOException {
        this.console = new ConsoleBuilder()
                .withPrompt("\u001B[1m>>> \u001B[0m")
                .withHandler(new JrshCompletionHandler())
                .withCompleters(getCompleters())
                .build();

        LogManager.getLogManager().reset();
    }

    @Override
    public void eval(@NonNull String[] args) throws Exception {

        ////////////////////////////////////////////////
        //                   Login                    //
        ////////////////////////////////////////////////
        String line = "login".concat(WHITE_SPACE).concat(args[0]);
        EvaluationResult result = parser.parse(line).eval().call();

        if (result.getCode() == ResultCode.FAILED) {
            console.println(result.getMessage());
            console.flush();
            System.exit(ResultCode.FAILED.getCode());
        } else {
            console.println(result.getMessage());
            console.flush();
        }


        ////////////////////////////////////////////////
        //                    REPL                    //
        ////////////////////////////////////////////////
        while (true) {
            line = console.readLine();
            if (line.isEmpty()) {
                console.print("");
                continue; // skip
            }
            try {
                Operation<? extends OperationParameters> operation = parser.parse(line);
                result = operation.eval().call();
                console.println(result.getMessage());
            } catch (Exception err) {
                console.println("error: " + err.getMessage());
            } finally {
                console.flush();
            }
        }
    }

    /**
     * See {@link EvaluationStrategy#setOperationParser(OperationParser)}.
     *
     * @param parser operation parser
     */
    @Override
    public void setOperationParser(@NonNull OperationParser parser) {
        this.parser = parser;
    }

    /**
     * Retrieves completers of the operations. Each operation has only one
     * aggregated grammar completer which is used to parse operation or
     * to configure console.
     *
     * @return completers
     */
    protected List<Completer> getCompleters() {
        List<Completer> completers = new ArrayList<>();
        for (Operation op : OperationFactory.getOperations()) {
            Completer cmt = op.getGrammar().getCompleter();
            completers.add(cmt);
        }
        return completers;
    }

}
