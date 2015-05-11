package ua.krasnyanskiy.jrsh.operation.grammar;

import lombok.EqualsAndHashCode;
import ua.krasnyanskiy.jrsh.operation.grammar.token.Token;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

/**
 * A grammar rule.
 *
 * @author Alexander Krasnyanskiy
 * @since 1.0
 */
@EqualsAndHashCode
public class Rule {

    private List<Token> tokens = new ArrayList<>();

    public Rule(Token... tokens) {
        this.tokens.addAll(asList(tokens));
    }

    public Token getToken(int tokenIndex) {
        return tokens.get(tokenIndex);
    }

    public List<Token> getTokens() {
        return tokens;
    }

    public void addToken(Token token) {
        tokens.add(token);
    }
}
