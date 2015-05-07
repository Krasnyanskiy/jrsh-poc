package ua.krasnyanskiy.jrsh.operation.parameter;

import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.importexport.exportservice.ExportParameter;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ua.krasnyanskiy.jrsh.operation.grammar.token.FileToken;
import ua.krasnyanskiy.jrsh.operation.grammar.token.RepositoryPathToken;
import ua.krasnyanskiy.jrsh.operation.parameter.annotation.Master;
import ua.krasnyanskiy.jrsh.operation.parameter.annotation.Parameter;
import ua.krasnyanskiy.jrsh.operation.parameter.converter.ExportParameterConverter;

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
public class ExportOperationParameters extends OperationParameters {

    @Parameter(name = "ctx", value = {"all", "role", "user", "repository"}, dependsOn = "export", mandatory = true)
    private String context;

    @Parameter(name = "repo_path", dependsOn = "repository", token = RepositoryPathToken.class, terminal = true)
    private String repositoryPath;

    @Parameter(name = "to", dependsOn = "repositoryPath")
    private boolean to;

    @Parameter(name = "file_path", dependsOn = "to", token = FileToken.class, terminal = true)
    private String filePath;

    @Parameter(dependsOn = "user")
    private List<String> users;

    @Parameter(dependsOn = "role")
    private List<String> roles;

    @Parameter(value = {"with-user-roles", "with-repository-permissions", "with-include-access-events", "with-include-audit-events", "with-include-monitoring-events"}, dependsOn = {"repository", "filePath"}, converter = ExportParameterConverter.class, terminal = true)
    private List<ExportParameter> exportParameters;
}
