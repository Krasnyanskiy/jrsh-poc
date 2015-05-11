package sandbox;

import org.assertj.core.api.Condition;
import org.junit.Test;
import ua.krasnyanskiy.jrsh.operation.grammar.Grammar;
import ua.krasnyanskiy.jrsh.operation.grammar.Rule;
import ua.krasnyanskiy.jrsh.operation.grammar.token.StringToken;
import ua.krasnyanskiy.jrsh.operation.parameter.AbstractOperationParameters;
import ua.krasnyanskiy.jrsh.operation.parameter.annotation.Master;
import ua.krasnyanskiy.jrsh.operation.parameter.annotation.Parameter;

import static org.assertj.core.api.Assertions.assertThat;

public class OperationGrammarFactoryTest {

    @Test
    public void shouldReturnGrammarForOperationWithFieldlessParameters() throws Exception {
        Grammar grammar = new OperationGrammarFactory().getGrammar(new FakeFieldlessOperationParameters());
        assertThat(grammar).isNotNull();
        assertThat(grammar.getRules()).are(new Condition<Rule>() {
            @Override
            public boolean matches(Rule given) {
                Rule expected = new Rule(new StringToken("fake"));
                return given.equals(expected);
            }
        });
    }

    @Test
    //@Ignore
    public void shouldReturnGrammarForOperation() throws Exception {
        Grammar grammar = new OperationGrammarFactory().getGrammar(new FakeOperationParameters());
        assertThat(grammar).isNotNull();
    }

    @Master(value = "fake", terminal = true)
    class FakeFieldlessOperationParameters extends AbstractOperationParameters {
        // No fields (except $this)
    }

    @Master("fake")
    class FakeOperationParameters extends AbstractOperationParameters {
        @Parameter(tokenName = "action", tokenValue = {"suck", "my", "dick"}, dependsOn = "fake")
        private String field;
    }
}
