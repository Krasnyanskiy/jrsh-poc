package ua.krasnyanskiy.jrsh.evaluation;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.NonNull;
import ua.krasnyanskiy.jrsh.common.ConsoleBuilder;
import ua.krasnyanskiy.jrsh.operation.EvaluationResult;
import ua.krasnyanskiy.jrsh.operation.EvaluationResult.ResultCode;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.lang.String.format;
import static java.lang.System.exit;

/**
 * @author Alexander Krasnyanskiy
 * @since 1.0
 */
@SuppressFBWarnings({"DM_EXIT"})
public class ScriptEvaluationStrategy extends AbstractEvaluationStrategy {

    private static final String ERROR_MSG = "error: line [\u001B[31m%s\u001B[0m], cause: [%s]";
    private static final String IO_ERROR_MSG = "error: Cannot read script (\u001B[1m%s\u001B[0m)";

    public ScriptEvaluationStrategy() {
        super.console = new ConsoleBuilder().build();
    }

    @Override
    public void eval(@NonNull String[] args) throws IOException {
        String scriptName = args[1];
        int counter = 1;
        try {
            Path path = Paths.get(scriptName);
            try (BufferedReader br = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
                for (String line; (line = br.readLine()) != null; counter++) {
                    if (!line.startsWith("#") && !line.trim().isEmpty()) {
                        EvaluationResult res = parseAndEvaluate(line);
                        if (res.getCode() == ResultCode.FAILED) {
                            console.println(format(ERROR_MSG, counter, res.getMessage()));
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
            console.println(format(IO_ERROR_MSG, scriptName));
            console.flush();
        }
    }
}