package ua.krasnyanskiy.jrsh.operation.grammar;

import org.jgrapht.graph.DefaultEdge;

/**
 * Simple Token Edge implementation.
 *
 * @param <T> token type
 * @author Alexander Krasnyanskiy
 * @since 1.0
 */
public class TokenEdge<T extends Token> extends DefaultEdge {

    private T source;
    private T target;

    public TokenEdge(T source, T target) {
        this.source = source;
        this.target = target;
    }

    @Override
    public T getSource() {
        return source;
    }

    @Override
    public T getTarget() {
        return target;
    }

    @Override
    public String toString() {
        return "(" + source + " : " + target + ")";
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

}
