package ua.krasnyanskiy.jrsh.operation.grammar.token;

import jline.console.completer.Completer;
import ua.krasnyanskiy.jrsh.completion.StaticRepositoryCompleter;

public class RepositoryPathToken extends ValueToken {

    public RepositoryPathToken(String name, boolean mandatory, boolean terminal) {
        super(name, mandatory, terminal);
    }

    public RepositoryPathToken(String name, String value, boolean mandatory, boolean terminal) {
        super(name, value, mandatory, terminal);
    }

    @Override
    public Completer getCompleter() {
        //return new MockRepositoryCompleter();
        return new StaticRepositoryCompleter();
    }

    @Override
    public boolean match(String name) {
        return true; // fixme
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
    public boolean isValueToken() {
        return true;
    }

    @Override
    public boolean isTerminal() {
        return terminal;
    }
}

