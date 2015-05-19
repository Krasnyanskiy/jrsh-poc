package com.jaspersoft.jasperserver.jrsh.core.operation.parser;

import com.jaspersoft.jasperserver.jrsh.core.operation.NoOperationParseException;
import com.jaspersoft.jasperserver.jrsh.core.operation.Operation;

public class Postconditions {
    public static void checkOperation(Operation operation) {
        if (operation == null) throw new NoOperationParseException();
    }
}
