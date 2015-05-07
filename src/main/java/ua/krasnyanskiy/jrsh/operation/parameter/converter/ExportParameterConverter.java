package ua.krasnyanskiy.jrsh.operation.parameter.converter;

import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.importexport.exportservice.ExportParameter;

import static com.jaspersoft.jasperserver.jaxrs.client.apiadapters.importexport.exportservice.ExportParameter.EVERYTHING;
import static com.jaspersoft.jasperserver.jaxrs.client.apiadapters.importexport.exportservice.ExportParameter.INCLUDE_ACCESS_EVENTS;
import static com.jaspersoft.jasperserver.jaxrs.client.apiadapters.importexport.exportservice.ExportParameter.INCLUDE_AUDIT_EVENTS;
import static com.jaspersoft.jasperserver.jaxrs.client.apiadapters.importexport.exportservice.ExportParameter.INCLUDE_MONITORING_EVENTS;
import static com.jaspersoft.jasperserver.jaxrs.client.apiadapters.importexport.exportservice.ExportParameter.REPOSITORY_PERMISSIONS;
import static com.jaspersoft.jasperserver.jaxrs.client.apiadapters.importexport.exportservice.ExportParameter.ROLE_USERS;

/**
 * @author Alexander Krasnyanskiy
 * @since 1.0
 */
public class ExportParameterConverter implements ParameterConverter<ExportParameter> {

    @Override
    public ExportParameter convert(String param) {
        switch (param) {
            case "all":
                return EVERYTHING;
            case "with-repository-permissions":
                return REPOSITORY_PERMISSIONS;
            case "with-user-roles":
                return ROLE_USERS;
            case "with-include-access-events":
                return INCLUDE_ACCESS_EVENTS;
            case "with-include-audit-events":
                return INCLUDE_AUDIT_EVENTS;
            case "with-include-monitoring-events":
                return INCLUDE_MONITORING_EVENTS;
            default:
                //return null;
                throw new RuntimeException(String.format("Wrong parameter (%s)", param));
        }
    }
}
