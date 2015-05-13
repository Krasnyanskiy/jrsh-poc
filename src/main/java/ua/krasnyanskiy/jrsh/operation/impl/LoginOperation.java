package ua.krasnyanskiy.jrsh.operation.impl;

import lombok.SneakyThrows;
import ua.krasnyanskiy.jrsh.common.SessionFactory.SessionBuilder;
import ua.krasnyanskiy.jrsh.operation.EvaluationResult;
import ua.krasnyanskiy.jrsh.operation.EvaluationResult.ResultCode;
import ua.krasnyanskiy.jrsh.operation.Operation;
import ua.krasnyanskiy.jrsh.operation.grammar.Grammar;
import ua.krasnyanskiy.jrsh.operation.grammar.OperationGrammarFactory;
import ua.krasnyanskiy.jrsh.operation.parameter.LoginOperationParameters;

import static java.lang.String.format;

public class LoginOperation implements Operation<LoginOperationParameters> {

    private final static String LOGIN_OK = "You've successfully logged in as (\u001B[1m%s\u001B[0m)";
    private final static String LOGIN_FAIL = "Login failed (\u001B[1m%s\u001B[0m)";

    private LoginOperationParameters parameters;
    private Grammar grammar;

    @SneakyThrows
    public LoginOperation() {
        this.grammar = OperationGrammarFactory.getGrammar(getParametersType());
    }

    public EvaluationResult eval() {
        EvaluationResult result;
        try {
            new SessionBuilder()
                    .withServer(parameters.getUrl())
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

    // fixme: move to app.properties
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
