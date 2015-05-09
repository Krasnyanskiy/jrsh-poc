package ua.krasnyanskiy.jrsh.operation.grammar.token;

import jline.console.completer.Completer;

/**
 * @author Alexander Krasnyanskiy
 * @since 1.0
 */
public interface Token {

    /**
     * Returns the completer for current token.
     */
    Completer getCompleter();

    /**
     * Matches given input and token tokenValue
     */
    boolean match(String name);

    /**
     * Internal name.
     * Example: repositoryPath
     */
    String getName();

    /**
     * The tokenValue.
     * Example: /puplic/Samples/Reports/06g.ProfitDetailReport
     */
    String getValue();

    /**
     * Flag for rule building
     */
    boolean isMandatory();

    /**
     * Checks of token is a tokenValue.
     * Example: /Users/alex/import.zip - is a tokenValue token, it doesn't have any prefix or key
     */
    boolean isValueToken();

    /**
     * Checks if token is the last on in the line
     */
    boolean isTerminal();
}
