package ua.krasnyanskiy.jrsh.operation.grammar.token;

import jline.console.completer.Completer;
import ua.krasnyanskiy.jrsh.completion.EmptyCompleter;

public class ValueToken extends StringToken {

    public ValueToken(String tknName) {
        super(tknName);
    }

    public ValueToken(String tknName, boolean mandatory) {
        super(tknName, mandatory);
    }

    public ValueToken(String tknName, boolean mandatory, boolean endPoint) {
        super(tknName, mandatory, endPoint);
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

    @Override
    public boolean isEndPoint() {
        return super.isEndPoint();
    }
}
