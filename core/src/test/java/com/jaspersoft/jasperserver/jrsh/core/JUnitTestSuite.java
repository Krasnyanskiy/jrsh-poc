package com.jaspersoft.jasperserver.jrsh.core;

import com.jaspersoft.jasperserver.jrsh.core.common.ArgumentConverterTest;
import com.jaspersoft.jasperserver.jrsh.core.evaluation.strategy.EvaluationStrategyTypeIdentifierTest;
import com.jaspersoft.jasperserver.jrsh.core.operation.OperationFactoryTest;
import com.jaspersoft.jasperserver.jrsh.core.operation.OperationReflectorTest;
import com.jaspersoft.jasperserver.jrsh.core.operation.parser.ConditionsTest;
import com.jaspersoft.jasperserver.jrsh.core.operation.parser.OperationGrammarParserTest;
import com.jaspersoft.jasperserver.jrsh.core.operation.parser.LL1OperationParserTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
        OperationFactoryTest.class,
        ArgumentConverterTest.class,
        EvaluationStrategyTypeIdentifierTest.class,
        OperationReflectorTest.class,
        LL1OperationParserTest.class,
        OperationGrammarParserTest.class,
        ConditionsTest.class
})
public class JUnitTestSuite {
}
