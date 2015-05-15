package refactoring;

/**
 * Represents a single rule of the grammar.
 *
 * @author Alexander Krasnyanskiy
 * @version 1.0
 */
public interface Rule {
    /**
     * Rule tokens
     *
     * @return tokens
     */
    Token[] getTokens();
}
