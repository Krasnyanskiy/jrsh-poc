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
public class LoginOperationParameters extends AbstractOperationParameters {

    @Prefix("--server")
    @Parameter(tokenName = "server", dependsOn = {"login", "username", "password", "organization"}, mandatory = true, terminal = true)
    private String server;

    @Prefix("--username")
    @Parameter(tokenName = "username", dependsOn = {"login", "server", "password", "organization"}, mandatory = true, terminal = true)
    private String username;

    @Prefix("--password")
    @Parameter(tokenName = "password", dependsOn = {"login", "server", "username", "organization"}, mandatory = true, terminal = true)
    private String password;

    @Prefix("--organization")
    @Parameter(tokenName = "organization", dependsOn = {"login", "server", "username", "password"}, terminal = true)
    private String organization;

    @Parameter(tokenName = "connectionString", dependsOn = "login", terminal = true)
    private String connectionString;

    public void setConnectionString(String line) {
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

        this.setServer(server);
        this.setUsername(username);
        this.setPassword(password);
        this.setOrganization(organization);
    }
}