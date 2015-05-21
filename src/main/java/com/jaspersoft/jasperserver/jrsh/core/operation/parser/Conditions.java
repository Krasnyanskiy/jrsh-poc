package com.jaspersoft.jasperserver.jrsh.core.operation.parser;

import com.jaspersoft.jasperserver.jrsh.core.operation.Operation;
import com.jaspersoft.jasperserver.jrsh.core.operation.parser.exception.NoGrammarRulesFoundException;
import com.jaspersoft.jasperserver.jrsh.core.operation.parser.exception.NoOperationFoundException;

public class Conditions {
    public static void checkOperation(Operation operation) {
        if (operation == null) throw new NoOperationFoundException();
    }

    public static void checkMatchedRules(boolean matchedRuleExist) {
        if (!matchedRuleExist) {
            throw new NoGrammarRulesFoundException();
        }
    }
}
