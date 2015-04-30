package ua.krasnyanskiy.jrsh.operation.parameter;

import com.jaspersoft.jasperserver.jaxrs.client.core.Session;
import lombok.Data;
import ua.krasnyanskiy.jrsh.common.SessionFactory;

@Data
public abstract class OperationParameters {
    private Session session = SessionFactory.getSharedSession();
}
