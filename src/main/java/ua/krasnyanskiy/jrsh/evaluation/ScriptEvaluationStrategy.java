package ua.krasnyanskiy.jrsh.evaluation;

import jline.console.ConsoleReader;
import lombok.NonNull;
import ua.krasnyanskiy.jrsh.common.ConsoleBuilder;
import ua.krasnyanskiy.jrsh.operation.EvaluationResult;
import ua.krasnyanskiy.jrsh.operation.Operation;
import ua.krasnyanskiy.jrsh.operation.parameter.OperationParameters;
import ua.krasnyanskiy.jrsh.operation.parser.OperationParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static java.lang.String.format;

/**
 * @author Alexander Krasnyanskiy
 * @since 1.0
 */
public class ScriptEvaluationStrategy implements EvaluationStrategy {

    private static final String COMMON_ERROR_MSG = "error in line [\u001B[31m%s\u001B[0m] cause: [%s]";
    private static final String IO_ERROR_MSG = "Cannot read script file";

    private OperationParser parser;
    private ConsoleReader console;

    public ScriptEvaluationStrategy() {
        this.console = new ConsoleBuilder().build();
    }

    @Override
    public void eval(@NonNull String[] tokens) throws IOException {

        int lineCounter = 1;
        String scriptName = tokens[1];
        File file = new File(scriptName);

        try {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                for (String line; (line = br.readLine()) != null; lineCounter++) {
                    if (line.startsWith("#") || line.trim().isEmpty()) {
                        continue; // skip comment
                    }
                    try {
                        Operation<? extends OperationParameters> op = parser.parse(line);
                        EvaluationResult res = op.eval().call();
                        console.println(res.getMessage());
                    } catch (Exception err) {
                        console.println(format(COMMON_ERROR_MSG, lineCounter, err.getMessage()));
                        console.flush();
                        System.exit(1);
                    } finally {
                        console.flush();
                    }
                }
            }
        } catch (IOException err) {
            console.println(IO_ERROR_MSG);
        }
    }

    @Override
    public void setOperationParser(OperationParser parser) {
        this.parser = parser;
    }
}
