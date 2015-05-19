package com.jaspersoft.jasperserver.jrsh.core.operation.grammar;

import com.jaspersoft.jasperserver.jrsh.core.operation.grammar.token.Token;

import java.util.ArrayList;
import java.util.List;

/**
 * A rule is a group tokens which we use in {@link Grammar}
 * to define the syntax of the operation.
 *
 * @author Alexander Krasnyanskiy
 * @version 1.0
 */
public interface Rule {

    /**
     * Returns a group of tokens.
     *
     * @return tokens
     */
    List<Token> getTokens();

    /**
     * Add a new token to the rule.
     *
     * @param token a new token
     */
    void addToken(Token token);


    class DefaultRule implements Rule {

        private List<Token> tokens;

        public DefaultRule() {
            tokens = new ArrayList<>();
        }

        @Override
        public List<Token> getTokens() {
            return tokens;
        }

        @Override
        public void addToken(Token token) {
            tokens.add(token);
        }

        @Override
        public String toString() {
            return tokens.toString();
        }
    }

}
