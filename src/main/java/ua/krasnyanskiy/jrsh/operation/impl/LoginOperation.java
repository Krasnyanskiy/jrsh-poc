package ua.krasnyanskiy.jrsh.operation.impl;

import jline.console.ConsoleReader;
import ua.krasnyanskiy.jrsh.common.SessionFactory;
import ua.krasnyanskiy.jrsh.operation.Operation;
import ua.krasnyanskiy.jrsh.operation.grammar.Grammar;
import ua.krasnyanskiy.jrsh.operation.parameter.LoginOperationParameters;

import java.util.concurrent.Callable;

import static java.lang.String.format;

public class LoginOperation implements Operation<LoginOperationParameters> {

    private LoginOperationParameters parameters;
    private Grammar grammar;
    //private ConsoleReader console;

    @Override
    public Callable<String> execute() {

        return new Callable<String>() {
            @Override
            public String call() throws Exception {
                try {
                    SessionFactory.createSharedSession(
                            parameters.getServer(),
                            parameters.getUsername(),
                            parameters.getPassword(),
                            parameters.getOrganization());
                    return "You've logged in.";
                } catch (Exception e) {
                    return format("Login failed (%s).", e.getMessage());
                }
            }
        };
    }

    @Override
    public Grammar getGrammar() {
        if (grammar != null) {
            return grammar;
        } else {
            // TODO: build grammar and save it
            return null;
        }
    }

    @Override
    public String getDescription() {
        return "This is Login Operation";
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
