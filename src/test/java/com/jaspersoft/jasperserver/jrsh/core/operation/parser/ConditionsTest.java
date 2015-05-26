package com.jaspersoft.jasperserver.jrsh.core.operation.parser;

import com.jaspersoft.jasperserver.jrsh.core.operation.impl.LoginOperation;
import com.jaspersoft.jasperserver.jrsh.core.operation.parser.exception.NoGrammarRulesFoundException;
import com.jaspersoft.jasperserver.jrsh.core.operation.parser.exception.NoOperationFoundException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ConditionsTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void shouldThrowAnExceptionInMatchedRuleDoesNotExist() {
        thrown.expect(NoGrammarRulesFoundException.class);
        boolean isMatchedRuleExist = false;
        Conditions.checkMatchedRules(isMatchedRuleExist);
    }

    @Test
    public void shouldDoNothingIfMatchedRuleIsExist() {
        boolean isMatchedRuleExist = true;
        Conditions.checkMatchedRules(isMatchedRuleExist);
    }

    @Test
    public void shouldThrowAnExceptionWhenOperationInNull() {
        thrown.expect(NoOperationFoundException.class);
        Conditions.checkOperation(null);
    }

    @Test
    public void shouldDoNothingIfOperationIsNotNull() {
        Conditions.checkOperation(new LoginOperation());
    }
}