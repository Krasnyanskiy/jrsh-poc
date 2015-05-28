package com.jaspersoft.jasperserver.jrsh.core.operation.parser.exception;

public class NoGrammarRulesFoundException extends OperationParseException {
    public NoGrammarRulesFoundException() {
        super("Cannot find rules for given operation.");
    }
}
