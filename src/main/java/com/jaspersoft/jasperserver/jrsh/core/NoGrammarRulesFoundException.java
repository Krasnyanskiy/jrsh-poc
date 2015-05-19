package com.jaspersoft.jasperserver.jrsh.core;

public class NoGrammarRulesFoundException extends RuntimeException {
    public NoGrammarRulesFoundException() {
        super("Can't find the rules for given operation.");
    }
}
