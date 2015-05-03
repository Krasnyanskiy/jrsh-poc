package ua.krasnyanskiy.jrsh.operation.impl;

import jline.console.ConsoleReader;
import ua.krasnyanskiy.jrsh.common.SessionFactory;
import ua.krasnyanskiy.jrsh.operation.Operation;
import ua.krasnyanskiy.jrsh.operation.OperationResult;
import ua.krasnyanskiy.jrsh.operation.grammar.Grammar;
import ua.krasnyanskiy.jrsh.operation.grammar.OperationSimpleGrammar;
import ua.krasnyanskiy.jrsh.operation.parameter.LoginOperationParameters;

import java.util.concurrent.Callable;

import static java.lang.String.format;
import static ua.krasnyanskiy.jrsh.operation.OperationResult.ResultCode.FAILED;
import static ua.krasnyanskiy.jrsh.operation.OperationResult.ResultCode.SUCCESS;

public class LoginOperation implements Operation<LoginOperationParameters> {

    private final static String LOGIN_OK = "You've successfully logged in as (\u001B[1m%s\u001B[0m)";
    private final static String LOGIN_FAIL = "Login failed (\u001B[1m%s\u001B[0m)";

    private LoginOperationParameters parameters;
    //private Grammar grammar;
    //private ConsoleReader console;

    @Override
    public Callable<OperationResult> execute() {

        return new Callable<OperationResult>() {
            @Override
            public OperationResult call() throws Exception {
                try {
                    SessionFactory.createSharedSession(
                            parameters.getServer(),
                            parameters.getUsername(),
                            parameters.getPassword(),
                            parameters.getOrganization());
                    return new OperationResult(format(LOGIN_OK, parameters.getUsername()), SUCCESS);
                } catch (Exception e) {
                    return new OperationResult(format(LOGIN_FAIL, e.getMessage()), FAILED);
                }
            }
        };
    }

    @Override
    public Grammar getGrammar() {
        //if (grammar != null) {
        //    return grammar;
        //} else {
            // TODO: build grammar and save it
            //return null;
        //}
        return new OperationSimpleGrammar();
    }

    @Override
    public String getDescription() {
        return "\t\u001B[1mLogin\u001B[0m makes a JRS REST client session which is used for interaction wih server.\n\tUsage: \u001B[37mlogin\u001B[0m --server <url> --username <name> --password <pass>";
    }

    @Override
    public Class<LoginOperationParameters> getParametersType() {
        return LoginOperationParameters.class;
    }

    @Override
    public void setOperationParameters(LoginOperationParameters parameters) {
        this.parameters = parameters;
    }

    @Override
    public void setConsole(ConsoleReader console) {
        // ignored
    }
}
