package ua.krasnyanskiy.jrsh.operation.grammar.token;

import jline.console.completer.Completer;

/**
 * @author Alexander Krasnyanskiy
 * @since 1.0
 */
public interface Token {

    String getName();

    String getValue();

    boolean isMandatory();

    boolean isTerminal();

    Completer getCompleter();

    boolean match(String name);
}
