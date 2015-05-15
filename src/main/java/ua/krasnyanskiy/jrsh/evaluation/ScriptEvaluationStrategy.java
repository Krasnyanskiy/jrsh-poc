package ua.krasnyanskiy.jrsh.evaluation;

import ua.krasnyanskiy.jrsh.common.ConsoleBuilder;
import ua.krasnyanskiy.jrsh.exception.ParseOperationParametersException;
import ua.krasnyanskiy.jrsh.operation.Operation;
import ua.krasnyanskiy.jrsh.operation.OperationResult;
import ua.krasnyanskiy.jrsh.operation.OperationResult.ResultCode;
import ua.krasnyanskiy.jrsh.operation.parameter.OperationParameters;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.lang.System.exit;

/**
 * @author Alexander Krasnyanskiy
 * @since 1.0
 */
public class ScriptEvaluationStrategy extends AbstractEvaluationStrategy {

    public ScriptEvaluationStrategy() {
        super.console = new ConsoleBuilder().build();
    }

    @Override
    public void eval(String[] appArgs) throws IOException, ParseOperationParametersException {
        String scriptName = appArgs[1];
        int counter = 1;
        try {
            Path path = Paths.get(scriptName);
            try (BufferedReader br = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
                for (String line; (line = br.readLine()) != null; counter++) {
                    if (!line.startsWith("#") && !line.trim().isEmpty()) {
                        OperationParameters params = parseParameters(line);
                        Operation operation = null;
                        OperationResult res = operation.eval(params);
                        if (res.getCode() == ResultCode.FAILED) {
                            console.println(res.getMessage());
                            console.flush();
                            exit(1);
                        } else {
                            console.println(res.getMessage());
                            console.flush();
                        }
                    }
                }
            }
        } catch (IOException e) {
            console.println("ERROR");
            console.flush();
        }
    }
}