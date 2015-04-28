package ua.krasnyanskiy.jrsh.operation.grammar;

import jline.console.completer.Completer;

/**
 * @author Alexander Krasnyanskiy
 */
public interface Token {

    Completer getCompleter();

    boolean match(String name);

    String getName();

    boolean isMandatory();

}
