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
import static java.lang.String.format;

public class ScriptEvaluationStrategy extends AbstractEvaluationStrategy {

    public static final String ERROR_MSG = "error on line: %s (%s)";
    private int lineCounter = 1;
    private ConsoleReader console;

    public ScriptEvaluationStrategy() {
        console = new ConsoleBuilder().build();
    }

    @Override
    public OperationResult eval(Script script) {
        List<String> source = script.getSource();
        OperationResult result = null;
        Operation operation = null;

        try {
            for (String line : source) {
                if (!line.startsWith("#") && !line.isEmpty()) {
                    Session session = SessionFactory.getSharedSession();
                    operation = parser.parse(line);
                    OperationResult temp = result;
                    result = operation.eval(session);
                    console.println(" → " + result.getResultMessage());
                    console.flush();
                    result.setPrevious(temp);
                }
                lineCounter++;
            }
        } catch (Exception err) {
            String message = format(ERROR_MSG, lineCounter, err.getMessage());
            try {
                console.print(message);
                console.flush();
            } catch (IOException ignored) {
            }
            result = new OperationResult(message, FAILED, operation, result);
        }
        return result;
    }
}
