package com.jaspersoft.jasperserver.jrsh.core;

        import com.jaspersoft.jasperserver.jrsh.core.operation.parser.ConditionsTest;
import com.jaspersoft.jasperserver.jrsh.core.operation.parser.GrammarMetadataParserTest;
import com.jaspersoft.jasperserver.jrsh.core.operation.parser.LL1OperationParserTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        LL1OperationParserTest.class,
        ConditionsTest.class,
        GrammarMetadataParserTest.class
})
public class JUnitTestSuite {
}
