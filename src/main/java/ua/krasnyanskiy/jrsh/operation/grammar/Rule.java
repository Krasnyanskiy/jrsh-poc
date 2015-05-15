package ua.krasnyanskiy.jrsh.operation.grammar;

import lombok.Data;
import ua.krasnyanskiy.jrsh.operation.grammar.token.Token;

import java.util.Collection;
import java.util.HashSet;

/**
 * A grammar rule.
 *
 * @author Alexander Krasnyanskiy
 * @since 1.0
 */
@Data
public class Rule {
    private Collection<Token> tokens = new HashSet<>();
}
