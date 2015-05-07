package ua.krasnyanskiy.jrsh.evaluation;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ua.krasnyanskiy.jrsh.operation.parser.LL1OperationParser;

public class ToolEvaluationStrategyTest {

    private EvaluationStrategy strategy;

    @Before
    public void before() {
        strategy = new ToolEvaluationStrategy();
        strategy.setOperationParser(new LL1OperationParser());
    }

    @Test
    public void should() throws Exception {
        strategy.eval(new String[]{"help", "export"});
    }

    @After
    public void after() {
        strategy = null;
    }
}