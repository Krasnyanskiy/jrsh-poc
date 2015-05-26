package com.jaspersoft.jasperserver.jrsh.core.evaluation;

import com.jaspersoft.jasperserver.jrsh.core.evaluation.strategy.EvaluationStrategyTypeIdentifier;
import com.jaspersoft.jasperserver.jrsh.core.evaluation.strategy.ScriptEvaluationStrategy;
import com.jaspersoft.jasperserver.jrsh.core.evaluation.strategy.ShellEvaluationStrategy;
import com.jaspersoft.jasperserver.jrsh.core.evaluation.strategy.ToolEvaluationStrategy;
import com.jaspersoft.jasperserver.jrsh.core.evaluation.strategy.EvaluationStrategy;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class EvaluationStrategyTypeIdentifierTest {

    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {new String[]{}, ToolEvaluationStrategy.class},
                {new String[]{"help"}, ToolEvaluationStrategy.class},
                {new String[]{"help", "export"}, ToolEvaluationStrategy.class},
                {new String[]{"--script", "/Users/sasha.botsman/magneto.jrs"}, ScriptEvaluationStrategy.class},
                {new String[]{"--script", "magneto.jrs"}, ScriptEvaluationStrategy.class},
                {new String[]{""}, ToolEvaluationStrategy.class},
                {new String[]{"user%password@localhost:8080/jasperserver-pro"}, ShellEvaluationStrategy.class},
                {new String[]{"user|org%password@localhost:8080/jasperserver-pro", "export", "repository", "/public"}, ToolEvaluationStrategy.class}
        });
    }

    private String[] parameters;
    private Class<? extends EvaluationStrategy> expectedType;

    public EvaluationStrategyTypeIdentifierTest(String[] parameters, Class<? extends EvaluationStrategy> expectedType) {
        this.parameters = parameters;
        this.expectedType = expectedType;
    }

    @Test
    public void shouldIdentifyEvaluationStrategyTypeBasedOnApplicationParameters() {
        Class<? extends EvaluationStrategy> retrievedType = EvaluationStrategyTypeIdentifier.identifyType(parameters);
        Assert.assertEquals(expectedType, retrievedType);
    }
}