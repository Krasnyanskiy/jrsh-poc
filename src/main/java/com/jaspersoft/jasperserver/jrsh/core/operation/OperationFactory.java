package com.jaspersoft.jasperserver.jrsh.core.operation;

import com.jaspersoft.jasperserver.jrsh.core.operation.impl.ExportOperation;
import com.jaspersoft.jasperserver.jrsh.core.operation.impl.HelpOperation;
import com.jaspersoft.jasperserver.jrsh.core.operation.impl.LoginOperation;
import com.jaspersoft.jasperserver.jrsh.core.operation.parser.exception.NoOperationFoundException;
import lombok.NonNull;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class OperationFactory {

    private static Map<String, Class<? extends Operation>> AVAILABLE_OPERATIONS = new HashMap<>();

    static {
        AVAILABLE_OPERATIONS.put("help", HelpOperation.class);
        AVAILABLE_OPERATIONS.put("export", ExportOperation.class);
        AVAILABLE_OPERATIONS.put("login", LoginOperation.class);
    }

    @NonNull
    public static Operation getOperationByName(String operationName) {
        Class<? extends Operation> operationType = AVAILABLE_OPERATIONS.get(operationName);
        if (operationType == null) {
            throw new NoOperationFoundException();
        }
        return create(operationType);
    }


    private static Operation create(Class<? extends Operation> operationType) {
        Operation instance = null;
        try {
            // don't forget to provide a default constructor to the operation
            instance = operationType.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return instance;
    }

    public static Set<Operation> getAvailableOperations() {
        Set<Operation> set = new HashSet<>();
        for (Class<? extends Operation> type : AVAILABLE_OPERATIONS.values()) {
            set.add(create(type));
        }
        return set;
    }
}
