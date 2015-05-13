package ua.krasnyanskiy.jrsh.operation.parameter;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ua.krasnyanskiy.jrsh.operation.grammar.token.ConnectionStringToken;
import ua.krasnyanskiy.jrsh.operation.parameter.annotation.Master;
import ua.krasnyanskiy.jrsh.operation.parameter.annotation.Parameter;
import ua.krasnyanskiy.jrsh.operation.parameter.annotation.Value;

/**
 * @author Alexander Krasnyanskiy
 * @since 1.0
 */
@Data
@Master(name = "login")
@EqualsAndHashCode(callSuper = false)
public class LoginOperationParameters extends AbstractOperationParameters {

    //@Prefix("--server")
    //@Parameter(mandatoryGroup = "U", mandatory = true, dependsOn = {"login", "name", "pass", "org"}, values = @Value(tokenName = "url", terminal = true, tokenClass = ValueToken.class))
    private String url;

    //@Prefix("--username")
    //@Parameter(mandatoryGroup = "N", mandatory = true, dependsOn = {"login", "url", "pass", "org"}, values = @Value(tokenName = "name", terminal = true, tokenClass = ValueToken.class))
    private String username;

    //@Prefix("--password")
    //@Parameter(mandatoryGroup = "P", mandatory = true, dependsOn = {"login", "url", "name", "org"}, values = @Value(tokenName = "pass", terminal = true, tokenClass = ValueToken.class))
    private String password;

    //@Prefix("--organization")
    //@Parameter(mandatory = false, dependsOn = {"login", "url", "name", "org", "pass"}, values = @Value(tokenName = "org", terminal = true, tokenClass = ValueToken.class))
    private String organization;

    @Parameter(mandatory = false, dependsOn = "login", values = @Value(tokenName = "CS", tokenClass = ConnectionStringToken.class, terminal = true))
    private String connectionString;

    public void setConnectionString(String line) {
        this.connectionString = line;
        String[] parts = line.split("[@]");
        String url = null, username = null, organization = null, password = null;

        if (parts.length == 2) {
            url = parts[1].trim();
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

        this.setUrl(url);
        this.setUsername(username);
        this.setPassword(password);
        this.setOrganization(organization);
    }
}