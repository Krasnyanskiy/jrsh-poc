package ua.krasnyanskiy.jrsh.operation.grammar;

import org.jgrapht.EdgeFactory;

/**
 * @author Alexander Krasnyanskiy
 */
public class TokenEdgeFactory implements EdgeFactory<Token, TokenEdge<Token>> {

    @Override
    public TokenEdge<Token> createEdge(Token source, Token target) {
        return new TokenEdge<>(source, target);
    }

}
