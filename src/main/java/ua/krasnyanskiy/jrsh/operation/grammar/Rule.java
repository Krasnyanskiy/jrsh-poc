package ua.krasnyanskiy.jrsh.operation.grammar;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

/**
 * @author Alexander Krasnyanskiy
 */
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
