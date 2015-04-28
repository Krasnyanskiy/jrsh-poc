package ua.krasnyanskiy.jrsh.evaluation.strategy;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class StrategyHelperTest {

    private StrategyHelper helper;

    @Before
    public void before() {
        helper = new StrategyHelper();
    }

    @Test
    public void shouldReturnProperStrategyWhenPassEmptyArray() {
        assertThat(helper.define()).isNotNull()
                .isExactlyInstanceOf(ToolEvaluationStrategy.class);
    }

    @Test
    public void shouldReturnProperStrategyWhenPassWrongLoginToken() {
        assertThat(helper.define("username+password+localhost:8080")).isNotNull()
                .isExactlyInstanceOf(ToolEvaluationStrategy.class);
    }

    @Test
    public void shouldReturnProperStrategyWhenPassValidLoginToken() {
        assertThat(helper.define("username%password@http://localhost:8080/jasperserver")).isNotNull()
                .isExactlyInstanceOf(ShellEvaluationStrategy.class);
    }

    @Test
    public void shouldReturnProperStrategyWhenPassScriptName() {
        assertThat(helper.define("eval", "my_script.jrs")).isNotNull()
                .isExactlyInstanceOf(ScriptEvaluationStrategy.class);
    }
}