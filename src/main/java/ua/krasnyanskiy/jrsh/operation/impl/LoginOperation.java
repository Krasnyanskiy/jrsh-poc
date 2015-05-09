package ua.krasnyanskiy.jrsh.operation.impl;

import ua.krasnyanskiy.jrsh.common.SessionFactory.SessionBuilder;
import ua.krasnyanskiy.jrsh.operation.EvaluationResult;
import ua.krasnyanskiy.jrsh.operation.EvaluationResult.ResultCode;
import ua.krasnyanskiy.jrsh.operation.Operation;
import ua.krasnyanskiy.jrsh.operation.grammar.Grammar;
import ua.krasnyanskiy.jrsh.operation.grammar.Rule;
import ua.krasnyanskiy.jrsh.operation.grammar.SimpleOperationGrammar;
import ua.krasnyanskiy.jrsh.operation.grammar.token.ConnectionStringToken;
import ua.krasnyanskiy.jrsh.operation.grammar.token.StringToken;
import ua.krasnyanskiy.jrsh.operation.grammar.token.Token;
import ua.krasnyanskiy.jrsh.operation.grammar.token.ValueToken;
import ua.krasnyanskiy.jrsh.operation.parameter.LoginOperationParameters;

import static java.lang.String.format;

public class LoginOperation implements Operation<LoginOperationParameters> {

    private final static String LOGIN_OK = "You've successfully logged in as (\u001B[1m%s\u001B[0m)";
    private final static String LOGIN_FAIL = "Login failed (\u001B[1m%s\u001B[0m)";

    private Grammar grammar;
    private LoginOperationParameters parameters;

    public LoginOperation() {
        setGrammar();
    }

    public EvaluationResult eval() {
        EvaluationResult result;
        try {
            // Create Session
            new SessionBuilder()
                    .withServer(parameters.getServer())
                    .withUsername(parameters.getUsername())
                    .withPassword(parameters.getPassword())
                    .withOrganization(parameters.getOrganization())
                    .buildSharedSession();
            result = new EvaluationResult(format(LOGIN_OK, parameters.getUsername()), ResultCode.SUCCESS, this);
        } catch (Exception err) {
            result = new EvaluationResult(format(LOGIN_FAIL, err.getMessage()), ResultCode.FAILED, this);
        }

        return result;
    }

    public Grammar getGrammar() {
        return grammar;
    }

    protected void setGrammar() {
        grammar = /*fixme*/new SimpleOperationGrammar();

        /** Hardcode **/
        Token connectionString = new ConnectionStringToken("connectionString", false, true);
        Token login = new StringToken("login", "login", /*соединять по зависимостям*/true, false); // [a->b->c] (a+b = mandatory)
        Token url = new StringToken("--server", "--server", true, false); // prefix token
        Token urlValue = new ValueToken("server-tokenValue", true, true);
        Token username = new StringToken("--username", "--username", true, false);
        Token usernameValue = new ValueToken("username-tokenValue", true, true);
        Token password = new StringToken("--password", "--password", true, false);
        Token passwordValue = new ValueToken("password-tokenValue", true, true);

        grammar.addRule(new Rule(login, connectionString));
        grammar.addRule(new Rule(login, url, urlValue, username, usernameValue, password, passwordValue));
        grammar.addRule(new Rule(login, url, urlValue, password, passwordValue, username, usernameValue));
        grammar.addRule(new Rule(login, username, usernameValue, url, urlValue, password, passwordValue));
        grammar.addRule(new Rule(login, username, usernameValue, password, passwordValue, url, urlValue));
        grammar.addRule(new Rule(login, password, passwordValue, username, usernameValue, url, urlValue));
        grammar.addRule(new Rule(login, password, passwordValue, url, urlValue, username, usernameValue));
    }

    public String getDescription() {
        return "\t\u001B[1mLogin\u001B[0m makes a JRS REST client session which is used for interaction wih server." +
                "\n\tUsage: \u001B[37mlogin\u001B[0m --server <url> --username <name> --password <pass>";
    }

    public Class<LoginOperationParameters> getParametersType() {
        return LoginOperationParameters.class;
    }

    public void setOperationParameters(LoginOperationParameters parameters) {
        this.parameters = parameters;
    }
}
