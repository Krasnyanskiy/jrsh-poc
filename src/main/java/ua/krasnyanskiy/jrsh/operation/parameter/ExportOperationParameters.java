package ua.krasnyanskiy.jrsh.operation.parameter;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ua.krasnyanskiy.jrsh.operation.grammar.token.FileToken;
import ua.krasnyanskiy.jrsh.operation.grammar.token.StringToken;
import ua.krasnyanskiy.jrsh.operation.parameter.annotation.Master;
import ua.krasnyanskiy.jrsh.operation.parameter.annotation.Parameter;
import ua.krasnyanskiy.jrsh.operation.parameter.annotation.Value;

/**
 * @author Alexander Krasnyanskiy
 * @since 1.0
 */
@Data
@Master(name = "export")
@EqualsAndHashCode(callSuper = false)
public class ExportOperationParameters extends AbstractOperationParameters {

    @Parameter(mandatoryGroup = "ctx", mandatory = true, dependsOn = "export", values = {@Value(tokenName = "repository", terminal = false, tokenClass = StringToken.class, tokenValue = "repository"), @Value(tokenName = "user", terminal = true, tokenClass = StringToken.class, tokenValue = "user"), @Value(tokenName = "all", terminal = true, tokenClass = StringToken.class, tokenValue = "all")})
    private String context;

    @Parameter(mandatoryGroup = "repo_path", mandatory = true, dependsOn = "repository", values = @Value(tokenName = "repositoryPath", terminal = true, tokenClass = FileToken.class))
    private String repositoryPath;

    @Parameter(mandatory = false, dependsOn = "repositoryPath", values = @Value(tokenName = "to", terminal = false, tokenClass = StringToken.class))
    private String to;

    @Parameter(mandatory = false, dependsOn = "to", values = @Value(tokenName = "uri", terminal = true, tokenClass = StringToken.class))
    private String fileUri;

    @Parameter(mandatory = false, dependsOn = {"uri", "repositoryPath", "wrp", "wime", "wiaue", "wiae"}, values = @Value(tokenName = "wur", terminal = true, tokenClass = StringToken.class, tokenValue = "with-user-roles"))
    private String withUserRoles;

    @Parameter(mandatory = false, dependsOn = {"uri", "repositoryPath", "wrp", "wime", "wiae", "wur"}, values = @Value(tokenName = "wiaue", terminal = true, tokenClass = StringToken.class, tokenValue = "with-include-audit-events"))
    private String withIncludeAuditEvents;

    @Parameter(mandatory = false, dependsOn = {"uri", "repositoryPath", "wrp", "wur", "wiaue", "wiae"}, values = @Value(tokenName = "wime", terminal = true, tokenClass = StringToken.class, tokenValue = "with-include-monitoring-events"))
    private String withIncludeMonitoringEvents;

    @Parameter(mandatory = false, dependsOn = {"uri", "repositoryPath", "wur", "wime", "wiaue", "wiae"}, values = @Value(tokenName = "wrp", terminal = true, tokenClass = StringToken.class, tokenValue = "with-repository-permissions"))
    private String withRepositoryPermissions;

    @Parameter(mandatory = false, dependsOn = {"uri", "repositoryPath", "wrp", "wime", "wur", "wiaue"}, values = @Value(tokenName = "wiae", terminal = true, tokenClass = StringToken.class, tokenValue = "with-include-access-events"))
    private String withIncludeAccessEvents;

}
