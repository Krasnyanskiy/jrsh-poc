package ua.krasnyanskiy.jrsh.operation;

import lombok.NonNull;
import ua.krasnyanskiy.jrsh.operation.impl.ExportOperation;
import ua.krasnyanskiy.jrsh.operation.impl.HelpOperation;
import ua.krasnyanskiy.jrsh.operation.impl.ImportOperation;
import ua.krasnyanskiy.jrsh.operation.impl.LoginOperation;

import java.util.HashMap;
import java.util.Map;

public class OperationFactory {

    private static final Map<String, Operation> operations = new HashMap<>();

    static {
        operations.put("export", new ExportOperation());
        operations.put("import", new ImportOperation());
        operations.put("help", new HelpOperation());
        operations.put("login", new LoginOperation());
    }

    @NonNull public static Operation getOperation(String operationName) {
        return operations.get(operationName);
    }

}
