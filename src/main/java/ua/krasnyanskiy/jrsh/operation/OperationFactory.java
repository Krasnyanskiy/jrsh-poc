package ua.krasnyanskiy.jrsh.operation;

import ua.krasnyanskiy.jrsh.operation.impl.ExportOperation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OperationFactory {

    private static final Map<String, Operation> operations;

    static {
        operations = new HashMap<String, Operation>() {{
            put("help", new ExportOperation());
            put("login", new ExportOperation());
            put("export", new ExportOperation());
            put("import", new ExportOperation());
        }};
    }

    public static Operation getOperation(String operationName) {
        return operations.get(operationName);
    }

    public static List<Operation> getOperations() {
        return new ArrayList<>(operations.values());
    }
}
