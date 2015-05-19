package com.jaspersoft.jasperserver.jrsh.core.operation.grammar.token;

import jline.console.completer.Completer;

/**
 * A token is a structure representing a lexeme that
 * explicitly indicates its categorization for the
 * purpose of parsing.
 *
 * @author Alex Krasnyanskiy
 * @version 1.0
 */
public interface Token {

    /**
     * Returns the name of token.
     *
     * @return name
     */
    String getName();

    /**
     * Return value of the token.
     *
     * @return value
     */
    String getValue();

    /**
     * Determines if token is mandatory for the group
     * of tokens in the rule.
     *
     * @return true if mandatory
     */
    boolean isMandatory();

    /**
     * Determines if token is tail of rule.
     *
     * @return true if tail
     */
    boolean isTailOfRule();

    /**
     * Returns a completer of the token.
     *
     * @return completer
     */
    Completer getCompleter();

    /**
     * Checks if the entered input corresponds
     * to the token value.
     *
     * @param input value
     * @return true if corresponds
     */
    boolean match(String input);

}
