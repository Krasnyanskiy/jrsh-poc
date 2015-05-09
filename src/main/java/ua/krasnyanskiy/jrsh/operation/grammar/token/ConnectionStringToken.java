package ua.krasnyanskiy.jrsh.operation.grammar.token;

import jline.console.completer.Completer;
import ua.krasnyanskiy.jrsh.completion.EmptyCompleter;

public class ConnectionStringToken extends StringToken {

    public ConnectionStringToken(String name, boolean mandatory, boolean terminal) {
        super(name, mandatory, terminal);
    }

    public ConnectionStringToken(String name, String value, boolean mandatory, boolean terminal) {
        super(name, value, mandatory, terminal);
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
        return true;
    }
}
