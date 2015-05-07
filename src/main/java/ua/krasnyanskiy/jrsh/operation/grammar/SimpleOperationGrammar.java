package ua.krasnyanskiy.jrsh.operation.grammar;

import jline.console.completer.Completer;
import lombok.Setter;
import ua.krasnyanskiy.jrsh.completion.CompleterBuilder;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Alexander Krasnyanskiy
 * @since 1.0
 */
public class SimpleOperationGrammar implements Grammar {

    @Setter
    private Collection<Rule> rules = new ArrayList<>();

    private Completer cmt;

    @Override
    public Collection<Rule> getRules() {
        return rules;
    }

    @Override
    public void addRule(Rule rule) {
        rules.add(rule);
    }

    @Override
    public Completer getCompleter() {
        if (cmt == null){
            cmt = new CompleterBuilder().withRules(rules).build();
        }
        return cmt;
    }
}
