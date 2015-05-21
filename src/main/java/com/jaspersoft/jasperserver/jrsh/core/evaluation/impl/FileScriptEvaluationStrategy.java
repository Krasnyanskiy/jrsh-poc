package com.jaspersoft.jasperserver.jrsh.core.evaluation.impl;

import com.jaspersoft.jasperserver.jrsh.core.common.ConsoleBuilder;
import com.jaspersoft.jasperserver.jrsh.core.evaluation.AbstractEvaluationStrategy;
import com.jaspersoft.jasperserver.jrsh.core.operation.Operation;
import com.jaspersoft.jasperserver.jrsh.core.operation.OperationResult;
import com.jaspersoft.jasperserver.jrsh.core.script.impl.FileScript;
import jline.console.ConsoleReader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * @author Alexander Krasnyanskiy
 * @since 1.0
 */
public class FileScriptEvaluationStrategy extends AbstractEvaluationStrategy<FileScript> {

    private int lineCounter = 1;
    private ConsoleReader console;

    public FileScriptEvaluationStrategy() {
        console = new ConsoleBuilder().build();
    }

    @Override
    public OperationResult eval(FileScript script) {
        File source = script.getSource();
        OperationResult last = null;
        Operation operation = null;

        try (BufferedReader br = new BufferedReader(new FileReader(source))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.startsWith("#") && !line.isEmpty()) {
                    operation = parser.parse(line);
                    OperationResult result = operation.eval();
                    console.println(" → " + result.getResultMessage());
                    console.flush();
                    last = (last != null) ? result.setPrevious(last) : result;
                }
                lineCounter++;
            }
        } catch (Exception err) {
            String message = String.format("error (%s) on line: \033[4;31m%s\u001B[0m", err.getMessage(), lineCounter);
            last = (last != null)
                    ? new OperationResult(message, OperationResult.ResultCode.FAILED, operation, last)
                    : new OperationResult(message, OperationResult.ResultCode.FAILED, operation, null);
        }
        return last;
    }
}
