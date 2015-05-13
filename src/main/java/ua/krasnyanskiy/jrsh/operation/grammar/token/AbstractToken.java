package ua.krasnyanskiy.jrsh.operation.grammar.token;

import jline.console.completer.Completer;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@EqualsAndHashCode(exclude = {"value", "mandatory", "terminal"})
public abstract class AbstractToken implements Token {

    protected String name;
    protected String value;
    protected boolean mandatory;
    protected boolean terminal;

    @Override
    public abstract Completer getCompleter();

    @Override
    public abstract boolean match(String name);

    @Override
    public String toString() {
        return name;
    }
}
