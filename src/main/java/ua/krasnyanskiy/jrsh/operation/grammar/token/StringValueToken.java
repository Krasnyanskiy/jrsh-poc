package ua.krasnyanskiy.jrsh.operation.grammar.token;

import jline.console.completer.Completer;
import ua.krasnyanskiy.jrsh.common.EmptyCompleter;

public class StringValueToken extends StringToken {

    public StringValueToken(String tknName) {
        super(tknName);
    }

    public StringValueToken(String tknName, boolean mandatory) {
        super(tknName, mandatory);
    }

    @Override
    public Completer getCompleter() {
        return new EmptyCompleter();
    }

    @Override
    public boolean isMandatory() {
        return super.isMandatory();
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public boolean match(String tName) {
        return true;
    }

    @Override
    public boolean isValueToken() {
        return true;
    }
}
