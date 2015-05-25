package com.jaspersoft.jasperserver.jrsh.core.evaluation.impl;

import com.jaspersoft.jasperserver.jaxrs.client.core.Session;
import com.jaspersoft.jasperserver.jrsh.core.common.ConsoleBuilder;
import com.jaspersoft.jasperserver.jrsh.core.common.SessionFactory;
import com.jaspersoft.jasperserver.jrsh.core.evaluation.AbstractEvaluationStrategy;
import com.jaspersoft.jasperserver.jrsh.core.operation.Operation;
import com.jaspersoft.jasperserver.jrsh.core.operation.OperationResult;
import com.jaspersoft.jasperserver.jrsh.core.script.Script;
import jline.console.ConsoleReader;

import java.io.IOException;
import java.util.List;

import static com.jaspersoft.jasperserver.jrsh.core.operation.OperationResult.ResultCode.*;

public class MultilineScriptEvaluationStrategy extends AbstractEvaluationStrategy {

    public static final String ERROR_MSG = "error on line: \033[4;31m%s\u001B[0m (%s)";
    private int lineCounter = 1;
    private ConsoleReader console;

    public MultilineScriptEvaluationStrategy() {
        console = new ConsoleBuilder().build();
    }

    @Override
    public OperationResult eval(Script script) {
        List<String> source = script.getSource();
        OperationResult last = null;
        Operation operation = null;

        try {
            for (String line : source) {
                if (!line.startsWith("#") && !line.isEmpty()) {
                    Session session = SessionFactory.getSharedSession();
                    operation = parser.parse(line);
                    OperationResult result = operation.eval(session);
                    console.println(" → " + result.getResultMessage());
                    console.flush();
                    last = (last != null) ? result.setPrevious(last) : result;
                }
                lineCounter++;
            }
        } catch (Exception err) {
            String message = String.format(ERROR_MSG, lineCounter, err.getMessage());
            try {
                console.print(message);
                console.flush();
            } catch (IOException ignored) {}
            return (last != null)
                    ? new OperationResult(message, FAILED, operation, last)
                    : new OperationResult(message, FAILED, operation, null);
        }
        return last;
    }
}
