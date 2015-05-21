package com.jaspersoft.jasperserver.jrsh.core;

import com.jaspersoft.jasperserver.jrsh.core.operation.parser.LL1OperationParserTest;
import com.jaspersoft.jasperserver.jrsh.core.script.ScriptOperationCompleterBuilderTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        LL1OperationParserTest.class,
        ScriptOperationCompleterBuilderTest.class
})
public class JUnitTestSuite {
        // empty
}
