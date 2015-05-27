package com.jaspersoft.jasperserver.jrsh.core.operation.parser.exception;

public class NoGrammarRulesFoundException extends OperationParseException {
    public NoGrammarRulesFoundException() {
        super("Cannot find a rule for given operation.");
    }
}
