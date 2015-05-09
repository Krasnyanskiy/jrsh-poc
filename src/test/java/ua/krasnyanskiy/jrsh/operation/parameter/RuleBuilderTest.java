package ua.krasnyanskiy.jrsh.operation.parameter;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import ua.krasnyanskiy.jrsh.operation.grammar.Rule;
import ua.krasnyanskiy.jrsh.operation.grammar.RuleBuilder;
import ua.krasnyanskiy.jrsh.operation.parameter.annotation.Master;
import ua.krasnyanskiy.jrsh.operation.parameter.annotation.Parameter;
import ua.krasnyanskiy.jrsh.operation.parameter.annotation.Prefix;

import java.util.Set;

public class RuleBuilderTest {

    private RuleBuilder builder;

    @Before
    public void before() {
        builder = new RuleBuilder();
    }

    @Test
    @Ignore
    public void shouldBuildRulesFromLoginOperationByParameters() throws Exception {
        Set<Rule> rules = builder.buildRules(new LoginOperationParameters());
        Assertions.assertThat(rules).isNotNull().isNotEmpty().hasSize(30);
    }

    @Test
    @Ignore
    public void shouldBuildRulesFromLoginExportByParameters() throws Exception {
        Set<Rule> rules = builder.buildRules(new ExportOperationParameters());
        Assertions.assertThat(rules).isNotNull().isNotEmpty().hasSize(30);
    }

    @Test
    @Ignore
    public void shouldBuildRulesFromFake1Params() throws Exception {
        Set<Rule> rules = builder.buildRules(new Fake1OperationParameters());
        Assertions.assertThat(rules).isNotNull().isNotEmpty().hasSize(1);
    }

    @After
    public void after() {
        builder = null;
    }


    @Data
    @Master("super")
    @EqualsAndHashCode(callSuper = false)
    private class Fake1OperationParameters extends AbstractOperationParameters {
        //@Parameter(tokenValue = "", mandatory = true, terminal = true)
        //private String status;
        @Prefix("--what?") @Parameter(tokenValue = "some", dependsOn = "super", mandatory = true, terminal = true)
        private String some;
    }
}