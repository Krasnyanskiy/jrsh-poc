package ua.krasnyanskiy.jrsh.operation.grammar.token;

import jline.console.completer.Completer;
import ua.krasnyanskiy.jrsh.completion.EmptyCompleter;

public class SshLoginToken implements Token {

    private String tknName;
    private final boolean mandatory;

    public SshLoginToken(String tknName, boolean mandatory) {
        this.tknName = tknName;
        this.mandatory = mandatory;
    }

    @Override
    public Completer getCompleter() {
        return new EmptyCompleter();
    }

    @Override
    public boolean match(String name) {
        return TokenPreconditions.isLoginToken(name);
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
    public boolean isValueToken() {
        return true;
    }

    @Override
    public boolean isEndPoint() {
        return true;
    }
}
