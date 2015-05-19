package com.jaspersoft.jasperserver.jrsh.core.operation;

public class NoOperationParseException extends OperationParseException {
    public NoOperationParseException() {
        super("No operation found!");
    }
}
