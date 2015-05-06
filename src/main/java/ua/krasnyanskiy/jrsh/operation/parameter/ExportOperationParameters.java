package ua.krasnyanskiy.jrsh.operation.parameter;

import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.importexport.exportservice.ExportParameter;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ua.krasnyanskiy.jrsh.operation.grammar.token.FileToken;
import ua.krasnyanskiy.jrsh.operation.grammar.token.RepositoryPathToken;
import ua.krasnyanskiy.jrsh.operation.parameter.annotation.Master;
import ua.krasnyanskiy.jrsh.operation.parameter.annotation.Parameter;
import ua.krasnyanskiy.jrsh.operation.parameter.converter.ExportParameterConverter;

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
@EqualsAndHashCode(callSuper = false)
public class ExportOperationParameters extends OperationParameters {

    @Master
    @Parameter(
            value = "export",
            mandatory = true)
    private String operationName;

    @Parameter(
            value = {"all", "role", "user", "repository"},
            dependsOn = "export",
            mandatory = true)
    private String context;

    // TODO: handle it in the next iteration
    @Parameter(dependsOn = "user") // what about values?
    private List<String> users;
    @Parameter(dependsOn = "role")
    private List<String> roles;

    @Parameter(
            value = "repositoryPath",
            dependsOn = "repository",
            token = RepositoryPathToken.class,
            endPoint = true)
    private String repositoryPath;

    @Parameter(
            value = "to",
            dependsOn = "repositoryPath")
    private boolean to;

    @Parameter(
            value = "filePath",
            dependsOn = "to",
            token = FileToken.class,
            endPoint = true)
    private String filePath;

    @Parameter(
            value = {
                    "with-user-roles",
                    "with-repository-permissions",
                    "with-include-access-events",
                    "with-include-audit-events",
                    "with-include-monitoring-events"
            },
            interconnected = true,
            dependsOn = {"repository", "filePath"},
            converter = ExportParameterConverter.class,
            endPoint = true)
    private List<ExportParameter> exportParameters = new ArrayList<>();


    /*
    @Parameter(
            value = "with-user-roles",
            dependsOn = {
                    "repository",
                    "filePath",
                    "with-repository-permissions",
                    "with-include-access-events",
                    "with-include-monitoring-events",
                    "with-include-audit-events"},
            endPoint = true,
            token = StringToken.class)
    private String withUserRoles;

    @Parameter(
            value = "with-repository-permissions",
            dependsOn = {
                    "repository",
                    "filePath",
                    "with-user-roles",
                    "with-include-access-events",
                    "with-include-monitoring-events",
                    "with-include-audit-events"},
            endPoint = true,
            token = StringToken.class)
    private String withRepositoryPermissions;

    @Parameter(
            value = "with-include-access-events",
            dependsOn = {
                    "repository",
                    "filePath",
                    "with-repository-permissions",
                    "with-include-access-events",
                    "with-include-monitoring-events",
                    "with-user-roles"},
            endPoint = true,
            token = StringToken.class)
    private String withIncludeAccessEvents;

    @Parameter(
            value = "with-include-audit-events",
            dependsOn = {
                    "repository",
                    "filePath",
                    "with-repository-permissions",
                    "with-include-access-events",
                    "with-include-monitoring-events",
                    "with-user-roles"},
            endPoint = true,
            token = StringToken.class)
    private String withIncludeAuditEvents;

    @Parameter(
            value = "with-include-monitoring-events",
            dependsOn = {
                    "repository",
                    "filePath",
                    "with-repository-permissions",
                    "with-include-access-events",
                    "with-user-roles",
                    "with-include-audit-events"},
            endPoint = true,
            token = StringToken.class)
    private String withIncludeMonitoringEvents;
    */
}
