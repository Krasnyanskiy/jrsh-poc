package com.jaspersoft.jasperserver.jrsh.core.operation;

import com.jaspersoft.jasperserver.jrsh.core.operation.OperationResult.ResultCode;
import com.jaspersoft.jasperserver.jrsh.core.operation.impl.ExportOperation;
import com.jaspersoft.jasperserver.jrsh.core.operation.impl.HelpOperation;
import lombok.NonNull;

import java.util.HashMap;
import java.util.Map;

public class OperationFactory {

    private static Map<String, Operation> AVAILABLE_OPERATIONS = new HashMap<>();

    static {
        AVAILABLE_OPERATIONS.put("help", new HelpOperation());
        AVAILABLE_OPERATIONS.put("export", new ExportOperation());
        AVAILABLE_OPERATIONS.put(
                "fake",
                new Operation() {
                    public OperationResult eval() {
                        return new OperationResult("Done!", ResultCode.SUCCESS, this, null);
                    }
                });
    }

    public static @NonNull Operation getOperationByName(String operationName) {
        return AVAILABLE_OPERATIONS.get(operationName);
    }
}
