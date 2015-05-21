package com.jaspersoft.jasperserver.jrsh.core.operation.parser.exception;

public class NoGrammarRulesFoundException extends OperationParseException {
    public NoGrammarRulesFoundException() {
        super("Can't find any rule for given operation.");
    }
}
