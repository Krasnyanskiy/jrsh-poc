package ua.krasnyanskiy.jrsh.operation.parameter;

import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.importexport.exportservice.ExportParameter;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ua.krasnyanskiy.jrsh.operation.grammar.token.FileToken;
import ua.krasnyanskiy.jrsh.operation.grammar.token.RepositoryPathToken;
import ua.krasnyanskiy.jrsh.operation.parameter.annotation.Master;
import ua.krasnyanskiy.jrsh.operation.parameter.annotation.Parameter;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is a simple combination of operation
 * metadata and DTO.
 *
 * @author Alexander Krasnyanskiy
 * @since 1.0
 */
@Data
@Master(value = "export")
@EqualsAndHashCode(callSuper = false)
public class ExportOperationParameters extends AbstractOperationParameters {

    @Parameter(tokenName = "context", tokenValue = {"all", "role", "user", "repository"}, dependsOn = "export", mandatory = true)
    private String context;

    @Parameter(tokenName = "repositoryPath", dependsOn = "repository", token = RepositoryPathToken.class, terminal = true)
    private String repositoryPath;

    @Parameter(tokenName = "to", dependsOn = "repositoryPath")
    private boolean to;

    @Parameter(tokenName = "filePath", dependsOn = "to", token = FileToken.class, terminal = true)
    private String filePath;

    @Parameter(tokenName = "withUserRoles", tokenValue = "with-user-roles", dependsOn = {"repository", "filePath"})
    public String withUserRoles;

    @Parameter(tokenName = "withRepositoryPermissions", tokenValue = "with-repository-permissions", dependsOn = {"repository", "filePath"})
    public String withRepositoryPermissions;

    @Parameter(tokenName = "withIncludeAccessEvents", tokenValue = "with-include-access-events", dependsOn = {"repository", "filePath"})
    public String withIncludeAccessEvents;

    @Parameter(tokenName = "withIncludeAuditEvents", tokenValue = "with-include-audit-events", dependsOn = {"repository", "filePath"})
    public String withIncludeAuditEvents;

    @Parameter(tokenName = "withIncludeMonitoringEvents", tokenValue = "with-include-monitoring-events", dependsOn = {"repository", "filePath"})
    public String withIncludeMonitoringEvents;

    private List<ExportParameter> exportParameters = new ArrayList<>();

    public void setTo(String ignored) {
        this.to = true;
    }

    public void setWithUserRoles(String ignored) {
        exportParameters.add(ExportParameter.ROLE_USERS);
    }

    public void setWithRepositoryPermissions(String ignored) {
        exportParameters.add(ExportParameter.REPOSITORY_PERMISSIONS);
    }

    public void setWithIncludeMonitoringEvents(String ignored) {
        exportParameters.add(ExportParameter.INCLUDE_MONITORING_EVENTS);
    }

    public void setWithIncludeAccessEvents(String ignored) {
        exportParameters.add(ExportParameter.INCLUDE_ACCESS_EVENTS);
    }

    public void setWithIncludeAuditEvents(String ignored) {
        exportParameters.add(ExportParameter.INCLUDE_AUDIT_EVENTS);
    }

}
