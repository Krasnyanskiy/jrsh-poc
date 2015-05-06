package ua.krasnyanskiy.jrsh.operation.grammar.token;

import jline.console.completer.Completer;

/**
 * @author Alexander Krasnyanskiy
 * @since 1.0
 */
public interface Token {

    Completer getCompleter();

    boolean match(String name);

    String getName();

    boolean isMandatory();

    boolean isValueToken();

    boolean isEndPoint();
}
