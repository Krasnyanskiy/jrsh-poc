package com.jaspersoft.jasperserver.jrsh.core.evaluation.impl;

import com.jaspersoft.jasperserver.jaxrs.client.core.Session;
import com.jaspersoft.jasperserver.jrsh.core.common.ConsoleBuilder;
import com.jaspersoft.jasperserver.jrsh.core.common.SessionFactory;
import com.jaspersoft.jasperserver.jrsh.core.completion.CompleterBuilder;
import com.jaspersoft.jasperserver.jrsh.core.completion.JrshCompletionHandler;
import com.jaspersoft.jasperserver.jrsh.core.evaluation.AbstractEvaluationStrategy;
import com.jaspersoft.jasperserver.jrsh.core.operation.Operation;
import com.jaspersoft.jasperserver.jrsh.core.operation.OperationFactory;
import com.jaspersoft.jasperserver.jrsh.core.operation.OperationResult;
import com.jaspersoft.jasperserver.jrsh.core.operation.grammar.Grammar;
import com.jaspersoft.jasperserver.jrsh.core.operation.impl.LoginOperation;
import com.jaspersoft.jasperserver.jrsh.core.operation.parser.GrammarMetadataParser;
import com.jaspersoft.jasperserver.jrsh.core.operation.parser.exception.OperationParseException;
import com.jaspersoft.jasperserver.jrsh.core.script.Script;
import jline.console.ConsoleReader;
import jline.console.UserInterruptException;
import jline.console.completer.Completer;

import java.io.IOException;

import static com.jaspersoft.jasperserver.jrsh.core.operation.OperationResult.ResultCode.FAILED;
import static com.jaspersoft.jasperserver.jrsh.core.operation.OperationResult.ResultCode.INTERRUPTED;

public class ShellEvaluationStrategy extends AbstractEvaluationStrategy {

    private ConsoleReader console;

    public ShellEvaluationStrategy() {
        //try {
        //FileHistory history = new FileHistory(new File("history/.jrshhistory"));
        this.console = new ConsoleBuilder()
                .withPrompt("$> ")
                .withHandler(new JrshCompletionHandler())
                .withInterruptHandling()
                .withCompleter(getCompleter())
              //.withHistory(history)
                .build();

        //} catch (IOException e) {
        //    System.err.println("WARNING: Failed to write operation history file: " + e.getMessage());
        //}
    }

    @Override
    public OperationResult eval(Script script) {
        String line = script.getSource().get(0);
        Operation operation = null;

        /*
        Signal interruptSignal = new Signal("INT");
        Signal.handle(interruptSignal, new SignalHandler() {
            @Override
            public void handle(Signal signal) {
                logout();
                System.exit(1);
            }
        });
        */

        /*
        // Hook
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                History h = console.getHistory();
                if (h instanceof FileHistory) {
                    try {
                        ((FileHistory) h).flush();
                    } catch (IOException e) {
                        System.err.println("WARNING: Failed to write command history file: " + e.getMessage());
                    }
                }
            }
        }));
        */

        OperationResult result = null;
        while (true) {
            try {
                Session session = SessionFactory.getSharedSession();
                if (line == null) {
                    line = console.readLine();
                }
                if (line.isEmpty()) {
                    print("");
                } else {
                    operation = parser.parse(line);
                    OperationResult temp = result;
                    result = operation.eval(session);
                    result.setPrevious(temp);
                    print(result.getResultMessage());
                }
                line = null;
            } catch (OperationParseException | IOException err) {
                if (operation instanceof LoginOperation && LoginOperation.counter <= 1) {
                    return new OperationResult("Login failed.", FAILED, operation, null);
                }
                try {
                    print(err.getMessage());
                } catch (IOException ignored) {
                } finally {
                    line = null;
                }
            } catch (UserInterruptException unimportant) {
                logout();
                return new OperationResult("Interrupted by user.", INTERRUPTED, operation, null);
            }
        }
    }

    protected void print(String message) throws IOException {
        console.println(message);
        console.flush();
    }

    protected Completer getCompleter() {
        CompleterBuilder completerBuilder = new CompleterBuilder();
        for (Operation operation : OperationFactory.getAvailableOperations()) {
            Grammar grammar = GrammarMetadataParser.parseGrammar(operation);
            completerBuilder.withOperationGrammar(grammar);
        }
        return completerBuilder.build();
    }

    protected void logout() {
        SessionFactory.getSharedSession().logout();
    }
}
