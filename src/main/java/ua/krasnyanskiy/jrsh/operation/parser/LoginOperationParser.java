package ua.krasnyanskiy.jrsh.operation.parser;

import ua.krasnyanskiy.jrsh.operation.Operation;
import ua.krasnyanskiy.jrsh.operation.impl.LoginOperation;
import ua.krasnyanskiy.jrsh.operation.parameter.LoginOperationParameters;
import ua.krasnyanskiy.jrsh.operation.parameter.OperationParameters;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginOperationParser implements OperationParser {

    @Override
    public Operation<? extends OperationParameters> parse(String line) {

        Pattern pattern = Pattern.compile("(login(\\s+)) ?(\\w+[|])?\\w+[%]\\w+[@]\\w(.)+");
        Matcher m = pattern.matcher(line);

        if (m.matches()){
            line = line.replace("login", "").trim();
        } else {
            return null;
        }

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
