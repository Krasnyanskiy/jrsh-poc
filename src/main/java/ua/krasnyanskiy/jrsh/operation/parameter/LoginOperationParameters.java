package ua.krasnyanskiy.jrsh.operation.parameter;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ua.krasnyanskiy.jrsh.operation.parameter.annotation.Dependency;
import ua.krasnyanskiy.jrsh.operation.parameter.annotation.Parameter;

/**
 * @author Alexander Krasnyanskiy
 * @since 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class LoginOperationParameters extends OperationParameters {

    public LoginOperationParameters() {}

    public LoginOperationParameters(String loginToken) {
        LoginToken lt = new LoginToken().tokenize(loginToken);
        this.server = lt.server;
        this.username = lt.username;
        this.password = lt.password;
        this.organization = lt.organization;
    }

    @Parameter(value = "login", mandatory = true)
    private String operationName;

    @Parameter(value = "--server", dependsOn = "login", ambivalent = true, dependencies = @Dependency("server-value"), mandatory = true)
    private String server;

    @Parameter(value = "--username", dependsOn = "login", ambivalent = true, dependencies = @Dependency("username-value"), mandatory = true)
    private String username;

    @Parameter(value = "--password", dependsOn = "login", ambivalent = true, dependencies = @Dependency("password-value"), mandatory = true)
    private String password;

    @Parameter(value = "--organization", dependsOn = "login", ambivalent = true, dependencies = @Dependency("organization-value"))
    private String organization;

    @Data
    public static class LoginToken {
        private String server;
        private String username;
        private String password;
        private String organization;

        public LoginToken tokenize(String lt) {
            String[] parts = lt.split("[@]");
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
            return this;
        }
    }
}
