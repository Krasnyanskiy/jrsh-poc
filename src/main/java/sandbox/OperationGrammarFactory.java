package sandbox;

import ua.krasnyanskiy.jrsh.operation.grammar.Grammar;
import ua.krasnyanskiy.jrsh.operation.grammar.Rule;
import ua.krasnyanskiy.jrsh.operation.grammar.SimpleOperationGrammar;
import ua.krasnyanskiy.jrsh.operation.grammar.token.StringToken;
import ua.krasnyanskiy.jrsh.operation.parameter.AbstractOperationParameters;
import ua.krasnyanskiy.jrsh.operation.parameter.annotation.Master;

import java.lang.reflect.Field;

public class OperationGrammarFactory {
    public Grammar getGrammar(AbstractOperationParameters param) throws Exception {

        Class<? extends AbstractOperationParameters> clazz = param.getClass();
        Field[] fields = clazz.getDeclaredFields();
        Master master = clazz.getAnnotation(Master.class);

        if (master == null) {
            throw new MissedMandatoryAnnotationException();
        }

        // exclude $this
        if (fields.length == 1 && master.terminal()) {
            Rule rule = new Rule(new StringToken(master.value()));
            return new SimpleOperationGrammar(rule);
        }

        throw new NoGrammarFoundException();
    }
}
