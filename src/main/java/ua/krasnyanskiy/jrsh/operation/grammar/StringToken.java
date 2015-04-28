package ua.krasnyanskiy.jrsh.operation.grammar;

import jline.console.completer.Completer;
import jline.console.completer.StringsCompleter;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class StringToken implements Token {

    private String tknName;
    private boolean mandatory;

    public StringToken(String tknName) {
        this.tknName = tknName;
    }

    public StringToken(String tknName, boolean mandatory) {
        this.tknName = tknName;
        this.mandatory = mandatory;
    }

    @Override
    public Completer getCompleter() {
        return new StringsCompleter(tknName);
    }

    @Override
    public boolean match(String tName) {
        return tknName.equals(tName);
    }

    @Override
    public String getName() {
        return tknName;
    }

    @Override
    public boolean isMandatory() {
        return mandatory;
    }

    @Override
    public String toString() {
        return tknName;
    }
}
