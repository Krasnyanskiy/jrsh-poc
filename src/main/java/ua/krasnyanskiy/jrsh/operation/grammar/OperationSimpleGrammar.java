package ua.krasnyanskiy.jrsh.operation.grammar;

import jline.console.completer.AggregateCompleter;
import jline.console.completer.ArgumentCompleter;
import jline.console.completer.Completer;
import jline.console.completer.NullCompleter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class OperationSimpleGrammar implements Grammar {

    @Setter
    private Collection<Rule> rules = new ArrayList<>();

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
        AggregateCompleter operation = new AggregateCompleter();
        ArgumentCompleter arg = new ArgumentCompleter();
        for (Rule rule : rules) {
            List<Token> tokens = rule.getTokens();
            for (Token token : tokens) {
                Completer fromToken = token.getCompleter();
                arg.getCompleters().add(fromToken);
            }
            arg.getCompleters().add(new NullCompleter());
            operation.getCompleters().add(arg);
            arg = new ArgumentCompleter();
        }
        return operation;
    }
}
