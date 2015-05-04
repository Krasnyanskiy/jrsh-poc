package ua.krasnyanskiy.jrsh.operation.impl;

import jline.console.ConsoleReader;
import ua.krasnyanskiy.jrsh.common.SessionFactory;
import ua.krasnyanskiy.jrsh.operation.Operation;
import ua.krasnyanskiy.jrsh.operation.OperationResult;
import ua.krasnyanskiy.jrsh.operation.OperationResult.ResultCode;
import ua.krasnyanskiy.jrsh.operation.grammar.Grammar;
import ua.krasnyanskiy.jrsh.operation.grammar.OperationSimpleGrammar;
import ua.krasnyanskiy.jrsh.operation.grammar.Rule;
import ua.krasnyanskiy.jrsh.operation.grammar.token.StringToken;
import ua.krasnyanskiy.jrsh.operation.grammar.token.StringValueToken;
import ua.krasnyanskiy.jrsh.operation.grammar.token.Token;
import ua.krasnyanskiy.jrsh.operation.parameter.LoginOperationParameters;

import java.util.concurrent.Callable;

import static java.lang.String.format;

public class LoginOperation implements Operation<LoginOperationParameters> {

    private final static String LOGIN_OK = "You've successfully logged in as (\u001B[1m%s\u001B[0m)";
    private final static String LOGIN_FAIL = "Login failed (\u001B[1m%s\u001B[0m)";

    private LoginOperationParameters parameters;
    private Grammar grammar;
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
                    return new OperationResult(format(LOGIN_OK, parameters.getUsername()), ResultCode.SUCCESS);
                } catch (Exception err) {
                    String message = err.getMessage();
                    if (message.contains("UnknownHostException")) { // don't want to parse it
                        message = "Wrong server URL";
                    }
                    return new OperationResult(format(LOGIN_FAIL,
                            message.equals("Not Found") ? "Wrong parameters" : message),
                            ResultCode.FAILED);
                }
            }
        };
    }

    @Override
    public Grammar getGrammar() {
        if (grammar != null) {
            return grammar;
        } else {
            // fixme
            Grammar grammar = new OperationSimpleGrammar();

            // hardcode

            Token login = new StringToken("login", true);
            Token url = new StringToken("--server", true);
            Token urlValue = new StringValueToken("server-value", true);
            Token username = new StringToken("--username", true);
            Token usernameValue = new StringValueToken("username-value", true);
            Token password = new StringToken("--password", true);
            Token passwordValue = new StringValueToken("password-value", true);

            grammar.addRule(new Rule(login, url, urlValue, username, usernameValue, password, passwordValue));
            grammar.addRule(new Rule(login, url, urlValue, password, passwordValue, username, usernameValue));
            grammar.addRule(new Rule(login, username, usernameValue, url, urlValue, password, passwordValue));
            grammar.addRule(new Rule(login, username, usernameValue, password, passwordValue, url, urlValue));
            grammar.addRule(new Rule(login, password, passwordValue, username, usernameValue, url, urlValue));
            grammar.addRule(new Rule(login, password, passwordValue, url, urlValue, username, usernameValue));

            this.grammar = grammar;
            return this.grammar;
        }
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
