package ua.krasnyanskiy.jrsh.operation.parser;

import ua.krasnyanskiy.jrsh.operation.Operation;
import ua.krasnyanskiy.jrsh.operation.impl.LoginOperation;
import ua.krasnyanskiy.jrsh.operation.parameter.LoginOperationParameters;
import ua.krasnyanskiy.jrsh.operation.parameter.OperationParameters;

public class PlainLoginOperationParser implements OperationParser {

    @Override
    public Operation<? extends OperationParameters> parse(String line) {
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

        if (server == null || username == null || password == null)
            throw new RuntimeException("Cannot parse login token.");

        Operation<LoginOperationParameters> login = new LoginOperation();
        LoginOperationParameters parameters = new LoginOperationParameters();
        
        parameters.setServer(server);
        parameters.setUsername(username);
        parameters.setPassword(password);
        parameters.setOrganization(organization);

        login.setOperationParameters(parameters);
        return login;
    }
}
