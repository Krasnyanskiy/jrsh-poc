package ua.krasnyanskiy.jrsh.operation.impl;

import jline.console.ConsoleReader;
import ua.krasnyanskiy.jrsh.common.SessionFactory;
import ua.krasnyanskiy.jrsh.operation.EvaluationResult;
import ua.krasnyanskiy.jrsh.operation.EvaluationResult.ResultCode;
import ua.krasnyanskiy.jrsh.operation.Operation;
import ua.krasnyanskiy.jrsh.operation.grammar.Grammar;
import ua.krasnyanskiy.jrsh.operation.grammar.Rule;
import ua.krasnyanskiy.jrsh.operation.grammar.SimpleOperationGrammar;
import ua.krasnyanskiy.jrsh.operation.grammar.token.SshLoginToken;
import ua.krasnyanskiy.jrsh.operation.grammar.token.StringToken;
import ua.krasnyanskiy.jrsh.operation.grammar.token.Token;
import ua.krasnyanskiy.jrsh.operation.grammar.token.ValueToken;
import ua.krasnyanskiy.jrsh.operation.parameter.LoginOperationParameters;

import java.util.concurrent.Callable;

import static java.lang.String.format;

public class LoginOperation implements Operation<LoginOperationParameters> {

    private final static String LOGIN_OK = "You've successfully logged in as (\u001B[1m%s\u001B[0m)";
    private final static String LOGIN_FAIL = "Login failed (\u001B[1m%s\u001B[0m)";

    private Grammar grammar;
    private LoginOperationParameters parameters;

    public Callable<EvaluationResult> eval() {
        return new Callable<EvaluationResult>() {
            public EvaluationResult call() throws Exception {
                try {
                    SessionFactory.createSharedSession(parameters.getServer(), parameters.getUsername(), parameters.getPassword(), parameters.getOrganization());
                    return new EvaluationResult(format(LOGIN_OK, parameters.getUsername()), ResultCode.SUCCESS);
                } catch (Exception err) {
                    String message = err.getMessage();
                    return new EvaluationResult(format(LOGIN_FAIL, message.equals("Not Found") ? "Wrong parameters" : message), ResultCode.FAILED);
                }
            }
        };
    }

    public Grammar getGrammar() {
        if (grammar != null) {
            return grammar;
        } else {
            grammar = /*fixme*/new SimpleOperationGrammar();

            /** Hardcode **/
            Token sshLogin = new SshLoginToken("login-value", true);
            Token login = new StringToken("login", true);
            Token url = new StringToken("--server", true);
            Token urlValue = new ValueToken("server-value", true);
            Token username = new StringToken("--username", true);
            Token usernameValue = new ValueToken("username-value", true);
            Token password = new StringToken("--password", true);
            Token passwordValue = new ValueToken("password-value", true);

            grammar.addRule(new Rule(login, sshLogin));
            grammar.addRule(new Rule(login, url, urlValue, username, usernameValue, password, passwordValue));
            grammar.addRule(new Rule(login, url, urlValue, password, passwordValue, username, usernameValue));
            grammar.addRule(new Rule(login, username, usernameValue, url, urlValue, password, passwordValue));
            grammar.addRule(new Rule(login, username, usernameValue, password, passwordValue, url, urlValue));
            grammar.addRule(new Rule(login, password, passwordValue, username, usernameValue, url, urlValue));
            grammar.addRule(new Rule(login, password, passwordValue, url, urlValue, username, usernameValue));

            return grammar;
        }
    }

    public String getDescription() {
        return "\t\u001B[1mLogin\u001B[0m makes a JRS REST client session which is used for interaction wih server.\n\tUsage: \u001B[37mlogin\u001B[0m --server <url> --username <name> --password <pass>";
    }

    public Class<LoginOperationParameters> getParametersType() {
        return LoginOperationParameters.class;
    }

    public void setOperationParameters(LoginOperationParameters parameters) {
        this.parameters = parameters;
    }

    @Override
    public void parseParameters(String line) {
        String[] parts = line.split("[@]");

        String server = null;
        String username = null;
        String organization = null;
        String password = null;

        if (parts.length == 2) {
            server = parts[1].trim();
            parts = parts[0].split("[%]");
            if (parts.length == 2) {
                password = parts[1].trim();
                parts = parts[0].split("[|]");
                if (parts.length == 2) {
                    username = parts[0].trim();
                    organization = parts[1].trim();
                } else if (parts.length == 1) {
                    username = parts[0].trim();
                }
            } else if (parts.length == 1) {
                parts = parts[0].split("[|]");
                if (parts.length == 2) {
                    username = parts[0].trim();
                    organization = parts[1].trim();
                } else {
                    username = parts[0].trim();
                }
            }
        }

        parameters = new LoginOperationParameters();

        parameters.setServer(server);
        parameters.setUsername(username);
        parameters.setPassword(password);
        parameters.setOrganization(organization);
    }

    public void setConsole(ConsoleReader console) {/*fixme*/}
}
