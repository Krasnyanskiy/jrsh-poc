package refactoring.impl.operation;

import com.jaspersoft.jasperserver.jaxrs.client.core.Session;
import refactoring.Operation;
import refactoring.impl.OperationResult;
import ua.krasnyanskiy.jrsh.operation.parameter.annotation.Master;

@Master
public class SaveOperation implements Operation {

    @Override
    public OperationResult eval(Session session) {
        return null;
    }
}
