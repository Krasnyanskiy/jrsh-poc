package ua.krasnyanskiy.jrsh.evaluation.strategy;

import org.junit.Test;

public class ScriptEvaluationStrategyTest {

    @Test(expected = NullPointerException.class)
    public void shouldThrowAnExceptionWhenPassNullToTheMethod() {
        new ScriptEvaluationStrategy().eval(null);
    }
}