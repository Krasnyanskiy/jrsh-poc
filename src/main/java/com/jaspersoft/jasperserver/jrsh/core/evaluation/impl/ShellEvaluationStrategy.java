package com.jaspersoft.jasperserver.jrsh.core.evaluation.impl;

import com.jaspersoft.jasperserver.jrsh.core.common.ConsoleBuilder;
import com.jaspersoft.jasperserver.jrsh.core.completion.JrshCompletionHandler;
import com.jaspersoft.jasperserver.jrsh.core.completion.OperationCompleterBuilder;
import com.jaspersoft.jasperserver.jrsh.core.evaluation.AbstractEvaluationStrategy;
import com.jaspersoft.jasperserver.jrsh.core.operation.Operation;
import com.jaspersoft.jasperserver.jrsh.core.operation.OperationFactory;
import com.jaspersoft.jasperserver.jrsh.core.operation.OperationResult;
import com.jaspersoft.jasperserver.jrsh.core.operation.OperationResult.ResultCode;
import com.jaspersoft.jasperserver.jrsh.core.operation.grammar.Grammar;
import com.jaspersoft.jasperserver.jrsh.core.operation.impl.LoginOperation;
import com.jaspersoft.jasperserver.jrsh.core.operation.parser.GrammarMetadataParser;
import com.jaspersoft.jasperserver.jrsh.core.operation.parser.exception.OperationParseException;
import com.jaspersoft.jasperserver.jrsh.core.script.impl.ShellOperationScript;
import jline.console.ConsoleReader;
import jline.console.UserInterruptException;
import jline.console.completer.Completer;
import lombok.experimental.NonFinal;

import java.io.IOException;

public class ShellEvaluationStrategy extends AbstractEvaluationStrategy<ShellOperationScript> {

    private ConsoleReader console;

    public ShellEvaluationStrategy() {
        this.console = new ConsoleBuilder()
                .withPrompt("\u001B[1m$> \u001B[0m")
                .withHandler(new JrshCompletionHandler())
                .withInterruptHandling()
                .withCompleter(getCompleter())
                .build();
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
                if (operation instanceof LoginOperation && LoginOperation.counter <= 1) {
                    System.exit(1);
                }
                try {
                    history = new OperationResult(err.getMessage(), ResultCode.FAILED, operation, history);
                    console.println(err.getMessage());
                    console.flush();
                    line = null;
                } catch (IOException ignored) {
                    line = null;
                }
            } catch (UserInterruptException unimportant) {
                // Ctrl+C handling
                // if (isHistoryOn) {
                return new OperationResult("Interrupted by user", ResultCode.INTERRUPTED, operation, history);
                // } else {
                //     System.exit(1);
                // }
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


    protected Completer getCompleter() {
        OperationCompleterBuilder completerBuilder = new OperationCompleterBuilder();
        for (Operation operation : OperationFactory.getAvailableOperations()) {
            Grammar grammar = GrammarMetadataParser.parseGrammar(operation);
            completerBuilder.withOperationGrammar(grammar);
        }
        return completerBuilder.build();
    }

}
