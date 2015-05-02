package ua.krasnyanskiy.jrsh.operation.grammar.edge;

import org.jgrapht.EdgeFactory;
import ua.krasnyanskiy.jrsh.operation.grammar.token.Token;

/**
 * @author Alexander Krasnyanskiy
 * @since 1.0
 */
public class TokenEdgeFactory implements EdgeFactory<Token, TokenEdge<Token>> {

    @Override
    public TokenEdge<Token> createEdge(Token source, Token target) {
        return new TokenEdge<>(source, target);
    }

}
