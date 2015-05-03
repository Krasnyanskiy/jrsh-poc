package ua.krasnyanskiy.jrsh.operation.parameter;

import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.importexport.exportservice.ExportParameter;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ua.krasnyanskiy.jrsh.operation.grammar.token.FileToken;
import ua.krasnyanskiy.jrsh.operation.grammar.token.RepositoryPathToken;
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

    @Parameter(value = "export", mandatory = true)
    private String operationName;

    @Parameter(dependsOn = "export", value = {"all", "role", "user", "repository"}, mandatory = true)
    private String context;

    // User
    @Parameter(dependsOn = "user"/*, ambivalent = true*/)
                                    // TODO: how to fix this?
                                    // export user [joeuser, bob]
                                    // - - - - - - - - - - - -
    private List<String> users; // names

    // Role
    @Parameter(dependsOn = "role")
    private List<String> roles;

    // Repository
    @Parameter(dependsOn = "repository", value = "repositoryPath", token = RepositoryPathToken.class, mandatory = true)
    private String repositoryPath;

    @Parameter(dependsOn = "repositoryPath", value = "to")
    private boolean to;

    @Parameter(dependsOn = "to", value = "filePath", token = FileToken.class, mandatory = true)
    private String filePath;

    @Parameter(interconnected = true, dependsOn = {"repository", "filePath"}, value = {"with-repository-permissions", "with-user-roles", "with-include-access-events", "with-include-audit-events", "with-include-monitoring-events", "with-repository-permissions"}, converter = ExportParameterConverter.class)
    private List<ExportParameter> exportParameters = new ArrayList<>();

}
