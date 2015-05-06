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
import java.util.concurrent.Callable;
import java.util.logging.LogManager;

import static java.lang.System.exit;

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
        /** log in to be able to evaluate operations in the interactive mode **/
        //Operation<? extends OperationParameters> operation = parser.parse(args[0]); // login
        //evalLogin(operation);

        String line = "login ".concat(args[0]);

        // infinite loop
        while (true) {

            if (line == null) {
                line = console.readLine();
            }
            if (line.isEmpty()) {
                console.print("");
                continue; // skip
            }
            try {
                Operation<? extends OperationParameters> operation = parser.parse(line);
                Callable<EvaluationResult> task = operation.eval();
                EvaluationResult result = task.call();
                console.println(result.getMessage());
            } catch (Exception err) {
                console.println("error: " + err.getMessage());
            } finally {
                console.flush();
            }
            line = null;
        }
    }

    /**
     * Evaluates login operation and prints result.
     *
     * @param login login operation
     * @throws Exception
     */
    protected void evalLogin(Operation<? extends OperationParameters> login) throws Exception {
        Callable<EvaluationResult> task = login.eval();
        EvaluationResult res = task.call();

        switch (res.getCode()) {
            case FAILED: {
                console.println(res.getMessage());
                console.flush();
                exit(ResultCode.FAILED.getCode());
            }
            case SUCCESS: {
                console.println(res.getMessage());
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
     * aggregated grammar completer. which is used to parse operation or
     * to configure the console.
     *
     * @return completers
     */
    protected List<Completer> getCompleters() {
        List<Completer> completers = new ArrayList<>();
        for (Operation op : OperationFactory.getOperations()) {
            Completer cmpltr = op.getGrammar().getCompleter();
            completers.add(cmpltr);
        }
        return completers;
    }

}
