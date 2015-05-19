package com.jaspersoft.jasperserver.jrsh.core.evaluation.impl;

import com.jaspersoft.jasperserver.jrsh.core.ConsoleBuilder;
import com.jaspersoft.jasperserver.jrsh.core.evaluation.AbstractEvaluationStrategy;
import com.jaspersoft.jasperserver.jrsh.core.operation.Operation;
import com.jaspersoft.jasperserver.jrsh.core.operation.OperationParseException;
import com.jaspersoft.jasperserver.jrsh.core.operation.OperationResult;
import com.jaspersoft.jasperserver.jrsh.core.operation.OperationResult.ResultCode;
import com.jaspersoft.jasperserver.jrsh.core.script.impl.ShellOperationScript;
import jline.console.ConsoleReader;
import jline.console.UserInterruptException;
import lombok.experimental.NonFinal;

import java.io.IOException;

public class ShellEvaluationStrategy extends AbstractEvaluationStrategy<ShellOperationScript> {

    private ConsoleReader console;

    public ShellEvaluationStrategy() {
        this.console = new ConsoleBuilder().withPrompt("$> ").withInterruptHandling().build();
    }

    @Override
    public OperationResult eval(@NonFinal ShellOperationScript script) {
        String line = script.getSource();
        OperationResult history = null;
        Operation operation = null;

        while (true) {
            try {
                if (line == null) {
                    line = console.readLine();
                }
                if (line.isEmpty()) {
                    console.println("");
                } else {
                    operation = parser.parse(line);
                    OperationResult current = operation.eval();
                    history = updateHistory(history, current);
                    console.println(current.getResultMessage());
                }
                console.flush();
                line = null;
            } catch (OperationParseException | IOException err) {
                try {
                    history = new OperationResult(err.getMessage(), ResultCode.FAILED, operation, history);
                    console.println(err.getMessage());
                    console.flush();
                } catch (IOException ignored) {
                    // NOP
                }
            } catch (UserInterruptException unimportant) {
                // Ctrl+C handling
                //if (historyAvailable) {
                return new OperationResult("Interrupted by user", ResultCode.INTERRUPTED, operation, history);
                //} else
                //System.exit(1);
            }
        }
    }

    protected OperationResult updateHistory(OperationResult resultHistory, OperationResult currentResult) {
        if (resultHistory != null) {
            resultHistory.setPrevious(currentResult);
            resultHistory = currentResult;
        } else {
            resultHistory = currentResult;
        }
        return resultHistory;
    }
}
