package com.jaspersoft.jasperserver.jrsh.core.operation.impl;

import com.jaspersoft.jasperserver.jaxrs.client.core.Session;
import com.jaspersoft.jasperserver.jrsh.core.common.SessionFactory;
import com.jaspersoft.jasperserver.jrsh.core.operation.Operation;
import com.jaspersoft.jasperserver.jrsh.core.operation.OperationResult;
import com.jaspersoft.jasperserver.jrsh.core.operation.OperationResult.ResultCode;
import com.jaspersoft.jasperserver.jrsh.core.operation.annotation.Master;
import com.jaspersoft.jasperserver.jrsh.core.operation.annotation.Parameter;
import com.jaspersoft.jasperserver.jrsh.core.operation.annotation.Value;
import com.jaspersoft.jasperserver.jrsh.core.operation.parser.exception.WrongConnectionStringFormatException;
import lombok.Data;

import static com.jaspersoft.jasperserver.jrsh.core.operation.grammar.token.TokenPreconditions.isConnectionString;

@Data
@Master(name = "login", description = "This is a login operation.")
public class LoginOperation implements Operation {

    public static int counter = 0;

//    @Prefix("--server")
//    @Parameter(
//            mandatory = true,
//            ruleGroups = {"SIMPLE_LOGIN_TOKENS_GROUP"},
//            dependsOn = {"login", "U", "P", "O"},
//            values = @Value(tokenAlias = "S", tail = true))
    private String server;

//    @Prefix("--username")
//    @Parameter(
//            mandatory = true,
//            ruleGroups = "SIMPLE_LOGIN_TOKENS_GROUP",
//            dependsOn = {"login", "S", "O", "P"},
//            values = @Value(tokenAlias = "U", tail = true))
    private String username;

//    @Prefix("--password")
//    @Parameter(
//            mandatory = true,
//            ruleGroups = "SIMPLE_LOGIN_TOKENS_GROUP",
//            dependsOn = {"login", "S", "U", "O"},
//            values = @Value(tokenAlias = "P", tail = true))
    private String password;

//    @Prefix("--organization")
//    @Parameter(
//            mandatory = false,
//            ruleGroups = "SIMPLE_LOGIN_TOKENS_GROUP",
//            dependsOn = {"login", "S", "U", "P"},
//            values = @Value(tokenAlias = "O", tail = true))
    private String organization;

    @Parameter(
            mandatory = true,
            //ruleGroups = "COMPLEX_LOGIN_TOKENS_GROUP",
            dependsOn = "login",
            values = @Value(tokenAlias = "CS", tail = true))
    private String connectionString;

    @Override
    public OperationResult eval(Session ignored) {
        OperationResult result;
        try {
            SessionFactory.createSharedSession(server, username, password, organization);
            result = new OperationResult(String.format("You have logged in as \u001B[1m%s\u001B[0m", username),
                    ResultCode.SUCCESS, this, null);
        } catch (Exception err) {
            result = new OperationResult(String.format("Session isn't established (%s)", err.getMessage()),
                    ResultCode.FAILED, this, null);
        } finally {
            counter++;
        }
        return result;
    }

    public void setConnectionString(String connectionString) {
        if (!isConnectionString(connectionString)) {
            throw new WrongConnectionStringFormatException();
        }
        this.connectionString = connectionString;
        String[] tokens = connectionString.split("[@]");
        switch (tokens.length) {
            case 2:
                server = tokens[1].trim();
                tokens = tokens[0].split("[%]");
                switch (tokens.length) {
                    case 2:
                        password = tokens[1].trim();
                        tokens = tokens[0].split("[|]");
                        switch (tokens.length) {
                            case 2:
                                username = tokens[0].trim();
                                organization = tokens[1].trim();
                                break;
                            case 1:
                                username = tokens[0].trim();
                                break;
                        }
                        break;
                    case 1:
                        tokens = tokens[0].split("[|]");
                        switch (tokens.length) {
                            case 2:
                                username = tokens[0].trim();
                                organization = tokens[1].trim();
                                break;
                            default:
                                username = tokens[0].trim();
                                break;
                        }
                        break;
                }
                break;
        }
    }

    public String getConnectionString() {
        return connectionString;
    }
}
