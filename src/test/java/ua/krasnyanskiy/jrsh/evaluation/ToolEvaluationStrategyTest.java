package ua.krasnyanskiy.jrsh.evaluation;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ua.krasnyanskiy.jrsh.operation.parser.OperationParserFacade;

public class ToolEvaluationStrategyTest {

    private EvaluationStrategy strategy;

    @Before
    public void before() {
        strategy = new ToolEvaluationStrategy();
        strategy.setOperationParser(new OperationParserFacade());
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