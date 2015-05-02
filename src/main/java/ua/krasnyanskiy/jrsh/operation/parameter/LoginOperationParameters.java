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

    /*
     * If there are dependencies, then build AggregateCompleter from two completers > example (key, value)
     */

    // login server [any] username [any] password [any]
    // in that case how we can configure [any]?
}
