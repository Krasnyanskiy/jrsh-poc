package ua.krasnyanskiy.jrsh.operation.grammar;

import jline.console.completer.Completer;
import lombok.Builder;
import lombok.Setter;
import ua.krasnyanskiy.jrsh.completion.CompleterBuilder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * @author Alexander Krasnyanskiy
 * @since 1.0
 */
@Builder
public class SimpleOperationGrammar implements Grammar {

    @Setter
    private Collection<Rule> rules = new ArrayList<>();

    private Completer cmt;

    public SimpleOperationGrammar() {
    }

    public SimpleOperationGrammar(Collection<Rule> rules, Completer cmt){
        this.rules = rules;
        this.cmt = cmt;
    }

    public SimpleOperationGrammar(Completer cmt){
        this.cmt = cmt;
    }

    public SimpleOperationGrammar(Rule... rules){
        Collections.addAll(this.rules, rules);
    }

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