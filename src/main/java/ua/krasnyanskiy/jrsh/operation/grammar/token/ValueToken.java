package ua.krasnyanskiy.jrsh.operation.grammar.token;

import jline.console.completer.Completer;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ua.krasnyanskiy.jrsh.completion.EmptyCompleter;

@Data
@EqualsAndHashCode(callSuper = true)
public class ValueToken extends AbstractToken {

    public ValueToken(String name, String value, boolean mandatory, boolean terminal) {
        super.name = name;
        super.value = value;
        super.mandatory = mandatory;
        super.terminal = terminal;
    }

    @Override
    public Completer getCompleter() {
        return new EmptyCompleter();
    }

    @Override
    public boolean match(String name) {
        return true;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
