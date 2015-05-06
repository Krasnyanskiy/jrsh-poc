package ua.krasnyanskiy.jrsh.operation.parameter;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ua.krasnyanskiy.jrsh.operation.parameter.annotation.Master;
import ua.krasnyanskiy.jrsh.operation.parameter.annotation.Parameter;
import ua.krasnyanskiy.jrsh.operation.parameter.annotation.Prefix;

/**
 * @author Alexander Krasnyanskiy
 * @since 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class LoginOperationParameters extends OperationParameters {

    @Master @Parameter(value = "login", mandatory = true)
    private String operationName = "login";

    @Prefix("--server")
    @Parameter(
            value = "server",
            dependsOn = {"login", "username", "password", "organization"},
            mandatory = true,
            endPoint = true)
    private String server;

    @Prefix("--username")
    @Parameter(
            value = "username",
            dependsOn = {"login", "server", "password", "organization"},
            mandatory = true,
            endPoint = true)
    private String username;

    @Prefix("--password")
    @Parameter(
            value = "password",
            dependsOn = {"login", "server", "username", "organization"},
            mandatory = true,
            endPoint = true)
    private String password;

    @Prefix("--organization")
    @Parameter(
            value = "organization",
            dependsOn = {"login", "server", "username", "password"},
            endPoint = true)
    private String organization;

}