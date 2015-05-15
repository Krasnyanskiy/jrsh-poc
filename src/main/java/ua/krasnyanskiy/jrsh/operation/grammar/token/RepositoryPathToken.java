package ua.krasnyanskiy.jrsh.operation.grammar.token;

import jline.console.completer.Completer;
import ua.krasnyanskiy.jrsh.completion.completer.RepositoryStaticCompleter;

public class RepositoryPathToken extends ValueToken {

    public RepositoryPathToken(String name, String value, boolean mandatory, boolean terminal) {
        super(name, value, mandatory, terminal);
    }

    @Override
    public Completer getCompleter() {
        return new RepositoryStaticCompleter();
    }

    @Override
    public boolean match(String name) {
        return true;
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
}

