package com.jaspersoft.jasperserver.jrsh.core.operation.impl;

import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.importexport.exportservice.ExportParameter;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.importexport.exportservice.ExportService;
import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.importexport.exportservice.ExportTaskAdapter;
import com.jaspersoft.jasperserver.jaxrs.client.core.Session;
import com.jaspersoft.jasperserver.jaxrs.client.dto.importexport.StateDto;
import com.jaspersoft.jasperserver.jrsh.core.operation.Operation;
import com.jaspersoft.jasperserver.jrsh.core.operation.OperationResult;
import com.jaspersoft.jasperserver.jrsh.core.operation.OperationResult.ResultCode;
import com.jaspersoft.jasperserver.jrsh.core.operation.annotation.Master;
import com.jaspersoft.jasperserver.jrsh.core.operation.annotation.Parameter;
import com.jaspersoft.jasperserver.jrsh.core.operation.annotation.Value;
import com.jaspersoft.jasperserver.jrsh.core.operation.grammar.token.impl.FileNameToken;
import com.jaspersoft.jasperserver.jrsh.core.operation.grammar.token.impl.RepositoryPathToken;
import com.jaspersoft.jasperserver.jrsh.core.operation.grammar.token.impl.StringToken;
import lombok.Data;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Data
@Master(name = "export", description = "This is an export operation.")
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
    public OperationResult eval(Session session) {
        OperationResult result;
        try {
            ExportService exportService = session.exportService();
            ExportTaskAdapter task = exportService.newTask();

            if ("repository".equals(context)) {
                if (repositoryPath != null) {
                    task.uri(repositoryPath);
                }
            }

            StateDto state = task
                    .parameters(convertExportParameters())
                    .create()
                    .getEntity();

            InputStream entity = session.exportService()
                    .task(state.getId())
                    .fetch()
                    .getEntity();

            if (to != null) {
                if (fileUri != null) {
                    File target = new File(fileUri);
                    FileUtils.copyInputStreamToFile(entity, target);
                }
            } else {
                File target = new File("export.zip");
                FileUtils.copyInputStreamToFile(entity, target);
            }
            result = new OperationResult("\033[1;94m✓ export done\033[0m", ResultCode.SUCCESS, this, null);
        } catch (Exception err) {
            result = new OperationResult("\033[1;31m✗ export failed\033[0m", ResultCode.FAILED, this, null);
        }

        return result;
    }

    protected List<ExportParameter> convertExportParameters() {
        List<ExportParameter> parameters = new ArrayList<>();
        if (withIncludeAccessEvents != null) {
            parameters.add(ExportParameter.INCLUDE_AUDIT_EVENTS);
        }
        if (withIncludeAuditEvents != null) {
            parameters.add(ExportParameter.INCLUDE_AUDIT_EVENTS);
        }
        if (withRepositoryPermissions != null) {
            parameters.add(ExportParameter.REPOSITORY_PERMISSIONS);
        }
        if (withUserRoles != null) {
            parameters.add(ExportParameter.ROLE_USERS);
        }
        if (withIncludeMonitoringEvents != null) {
            parameters.add(ExportParameter.INCLUDE_MONITORING_EVENTS);
        }
        return parameters;
    }
}
