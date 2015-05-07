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
@Master("login")
@EqualsAndHashCode(callSuper = false)
public class LoginOperationParameters extends OperationParameters {

    @Prefix("--server")
    @Parameter(name = "server", dependsOn = {"login", "username", "password", "organization"}, mandatory = true, terminal = true)
    private String server;

    @Prefix("--username")
    @Parameter(name = "username", dependsOn = {"login", "server", "password", "organization"}, mandatory = true, terminal = true)
    private String username;

    @Prefix("--password")
    @Parameter(name = "password", dependsOn = {"login", "server", "username", "organization"}, mandatory = true, terminal = true)
    private String password;

    @Prefix("--organization")
    @Parameter(name = "organization", dependsOn = {"login", "server", "username", "password"}, terminal = true)
    private String organization;

}