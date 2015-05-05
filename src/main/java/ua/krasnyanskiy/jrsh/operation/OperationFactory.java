package ua.krasnyanskiy.jrsh.operation;

import ua.krasnyanskiy.jrsh.operation.impl.ExportOperation;
import ua.krasnyanskiy.jrsh.operation.impl.HelpOperation;
import ua.krasnyanskiy.jrsh.operation.impl.LoginOperation;
import ua.krasnyanskiy.jrsh.operation.parameter.OperationParameters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OperationFactory {

    private static final Map<String, Operation> operations;

    static {
        operations = new HashMap<String, Operation>() {{
            put("help", new HelpOperation());
            put("login", new LoginOperation());
            put("export", new ExportOperation());
        }};
    }

    public static Operation<OperationParameters> getOperation(String operationName) {
        return operations.get(operationName);
    }

    public static List<Operation> getOperations() {
        return new ArrayList<>(operations.values());
    }
}
