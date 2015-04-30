package ua.krasnyanskiy.jrsh.operation.parameter;

import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.importexport.exportservice.ExportParameter;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ua.krasnyanskiy.jrsh.operation.parameter.annotation.Interconnected;
import ua.krasnyanskiy.jrsh.operation.parameter.annotation.Master;
import ua.krasnyanskiy.jrsh.operation.parameter.annotation.Parameter;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class ExportOperationParameters extends OperationParameters {

    @Master
    private final String name = "export";

    @Parameter(dependsOn = "name")
    private boolean role;

    @Parameter(dependsOn = "name")
    private boolean user;

    @Parameter(dependsOn = "name")
    private boolean all;

    @Parameter(dependsOn = "name")
    private boolean repository;

    @Parameter(dependsOn = "repositoryPath")
    private Boolean to;

    @Parameter(dependsOn = "repository")
    private String repositoryPath;

    @Parameter(mandatory = true, dependsOn = "to")
    private String filePath;

    @Interconnected
    @Parameter(dependsOn = {"repository", "filePath"})
    private List<ExportParameter> exportParameters;

}
