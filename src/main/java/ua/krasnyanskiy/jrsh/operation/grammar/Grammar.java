package ua.krasnyanskiy.jrsh.operation.grammar;

import jline.console.completer.Completer;

import java.util.Collection;

/**
 * @author Alexander Krasnyanskiy
 * @since 1.0
 */
public interface Grammar {

    Collection<Rule> getRules();

    void addRule(Rule rule);

    Completer getCompleter();
}
