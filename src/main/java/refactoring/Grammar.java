package refactoring;

/**
 * Operation grammar is used for operation parsing and for
 * setup autocompletion environment.
 *
 * @author Alexander Krasnyanskiy
 * @version 1.0
 */
public interface Grammar {

    Rule[] getRules();

}
