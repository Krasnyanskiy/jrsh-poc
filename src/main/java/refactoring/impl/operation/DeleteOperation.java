package refactoring.impl.operation;

import com.jaspersoft.jasperserver.jaxrs.client.core.Session;
import refactoring.Operation;
import refactoring.impl.OperationResult;
import ua.krasnyanskiy.jrsh.operation.parameter.annotation.Master;
import ua.krasnyanskiy.jrsh.operation.parameter.annotation.Parameter;
import ua.krasnyanskiy.jrsh.operation.parameter.annotation.Value;

@Master(terminal = false,
        description = "Delete operation is used for content deleting. " +
                      "Usage: $> delete <content> -o <parameter>")
public class DeleteOperation implements Operation {

    @Parameter(values = {@Value(tokenValue = "--c1"), @Value(tokenValue = "--x2")})
    private String content;

    @Override
    public OperationResult eval(Session session) {
        return null;
    }
}
