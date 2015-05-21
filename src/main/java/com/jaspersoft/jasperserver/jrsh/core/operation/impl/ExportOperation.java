package com.jaspersoft.jasperserver.jrsh.core.operation.impl;

import com.jaspersoft.jasperserver.jrsh.core.operation.Operation;
import com.jaspersoft.jasperserver.jrsh.core.operation.OperationResult;
import com.jaspersoft.jasperserver.jrsh.core.operation.annotation.Master;
import com.jaspersoft.jasperserver.jrsh.core.operation.annotation.Parameter;
import com.jaspersoft.jasperserver.jrsh.core.operation.annotation.Value;
import com.jaspersoft.jasperserver.jrsh.core.operation.grammar.token.impl.FileNameToken;
import com.jaspersoft.jasperserver.jrsh.core.operation.grammar.token.impl.RepositoryPathToken;
import com.jaspersoft.jasperserver.jrsh.core.operation.grammar.token.impl.StringToken;
import lombok.Data;

@Data
@Master(name = "export", description = "none")
public class ExportOperation implements Operation {

    @Parameter(mandatory = true, dependsOn = "export", values = {
            @Value(tokenAlias = "RE", tokenClass = StringToken.class, tokenValue = "repository"),
            @Value(tokenAlias = "RO", tokenClass = StringToken.class, tokenValue = "role"),
            @Value(tokenAlias = "U", tokenClass = StringToken.class, tokenValue = "user"),
            @Value(tokenAlias = "A", tokenClass = StringToken.class, tokenValue = "all")
    })
    private String context;

    @Parameter(mandatory = true, dependsOn = "RE", values =
    @Value(tokenAlias = "RP", tokenClass = RepositoryPathToken.class, tail = true))
    private String repositoryPath;

    @Parameter(dependsOn = "RP", values =
    @Value(tokenAlias = "->", tokenClass = StringToken.class, tokenValue = "to"))
    private String to;

    @Parameter(dependsOn = "->", values =
    @Value(tokenAlias = "F", tokenClass = FileNameToken.class, tail = true))
    private String fileUri;

    @Parameter(dependsOn = {"F", "RP", "IUR", "IME", "RPP", "IAE"}, values =
    @Value(tokenAlias = "UR", tokenClass = StringToken.class, tokenValue = "with-user-roles", tail = true))
    private String withUserRoles;

    @Parameter(dependsOn = {"F", "RP", "UR", "IME", "RPP", "IAE"}, values =
    @Value(tokenAlias = "IUR", tokenClass = StringToken.class, tokenValue = "with-include-audit-events", tail = true))
    private String withIncludeAuditEvents;

    @Parameter(dependsOn = {"F", "RP", "UR", "IUR", "RPP", "IAE"}, values =
    @Value(tokenAlias = "IME", tokenClass = StringToken.class, tokenValue = "with-include-monitoring-events", tail = true))
    private String withIncludeMonitoringEvents;

    @Parameter(dependsOn = {"F", "RP", "UR", "IUR", "IME", "IAE"}, values =
    @Value(tokenAlias = "RPP", tokenClass = StringToken.class, tokenValue = "with-repository-permissions", tail = true))
    private String withRepositoryPermissions;

    @Parameter(dependsOn = {"F", "RP", "UR", "IUR", "RPP", "IME"}, values =
    @Value(tokenAlias = "IAE", tokenClass = StringToken.class, tokenValue = "with-include-access-events", tail = true))
    private String withIncludeAccessEvents;

    @Override
    public OperationResult eval() {
        return new OperationResult("Test", OperationResult.ResultCode.SUCCESS, this, null);
    }
}
