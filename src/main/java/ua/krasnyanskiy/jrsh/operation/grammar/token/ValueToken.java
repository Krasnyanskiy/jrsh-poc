package ua.krasnyanskiy.jrsh.operation.grammar.token;

import jline.console.completer.Completer;
import ua.krasnyanskiy.jrsh.completion.EmptyCompleter;

/**
 * Example: /public/Samples/abc - is a tokenValue token without any prefix of key
 */
public class ValueToken extends StringToken {

    public ValueToken(String name) {
        super(name);
    }

    public ValueToken(String name, boolean mandatory) {
        super(name, mandatory);
    }

    public ValueToken(String name, String value, boolean mandatory) {
        super(name, value, mandatory);
    }

    public ValueToken(String name, boolean mandatory, boolean terminal) {
        super(name, mandatory, terminal);
    }

    public ValueToken(String name, String value, boolean mandatory, boolean terminal) {
        super(name, value, mandatory, terminal);
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
    public String getValue() {
        return value;
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
    public boolean isTerminal() {
        return super.isTerminal();
    }
}
