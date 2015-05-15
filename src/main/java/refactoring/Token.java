package refactoring;

import jline.console.completer.Completer;

/**
 * Base interface of grammar rule token.
 *
 * @author Alexander Krasnyanskiy
 * @version 1.0
 */
public interface Token {

    String getName();
    String getValue();

    boolean isMandatory();
    boolean isTerminal();

    Completer getCompleter();
    boolean match(String tName);
}
