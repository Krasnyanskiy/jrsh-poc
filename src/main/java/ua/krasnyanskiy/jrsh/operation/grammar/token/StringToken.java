package ua.krasnyanskiy.jrsh.operation.grammar.token;

import jline.console.completer.Completer;
import jline.console.completer.StringsCompleter;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(exclude = {"mandatory", "terminal"})
public class StringToken implements Token {

    protected String name;
    protected String value;
    protected boolean mandatory;
    protected boolean terminal;

    public StringToken(String name, String value, boolean mandatory, boolean terminal) {
        this.name = name;
        this.value = value;
        this.mandatory = mandatory;
        this.terminal = terminal;
    }

    @Override
    public Completer getCompleter() {
        return new StringsCompleter(/*name*/value);
    }

    @Override
    public boolean match(String tName) {
        //return name.equals(tName);
        return value.equals(tName);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public boolean isMandatory() {
        return mandatory;
    }

    @Override
    public boolean isTerminal() {
        return terminal;
    }

    @Override
    public String toString() {
        return name;
    }
}
