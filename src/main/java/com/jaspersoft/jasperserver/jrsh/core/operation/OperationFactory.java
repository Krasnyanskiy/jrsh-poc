package com.jaspersoft.jasperserver.jrsh.core.operation;

import com.jaspersoft.jasperserver.jrsh.core.operation.impl.ExportOperation;
import com.jaspersoft.jasperserver.jrsh.core.operation.impl.HelpOperation;
import com.jaspersoft.jasperserver.jrsh.core.operation.impl.LoginOperation;
import com.jaspersoft.jasperserver.jrsh.core.operation.parser.exception.NoOperationFoundException;
import lombok.NonNull;

import java.util.HashMap;
import java.util.Map;

public class OperationFactory {

    private static Map<String, Class<? extends Operation>> AVAILABLE_OPERATIONS = new HashMap<>();

    static {
        AVAILABLE_OPERATIONS.put("help", HelpOperation.class);
        AVAILABLE_OPERATIONS.put("export", ExportOperation.class);
        AVAILABLE_OPERATIONS.put("login", LoginOperation.class);
    }

    @NonNull
    public static Operation getOperationByName(String operationName) {
        Operation instance = null;
        Class<? extends Operation> operationClass = AVAILABLE_OPERATIONS.get(operationName);

        if (operationClass == null) {
            throw new NoOperationFoundException();
        } else {
            try {
                instance = operationClass.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace(); // don't forget to add default constructor to your new operation
            }
        }

        return instance;

    }
}
