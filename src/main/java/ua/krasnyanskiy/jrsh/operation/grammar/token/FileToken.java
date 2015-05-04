package ua.krasnyanskiy.jrsh.operation.grammar.token;

import jline.console.completer.Completer;
import ua.krasnyanskiy.jrsh.common.CustomFileCompleter;

public class FileToken implements Token {

    private String tokenName = "filePath";
    private boolean mandatory;

    public FileToken(String tokenName, boolean mandatory) {
        this.tokenName = tokenName;
        this.mandatory = mandatory;
    }

    @Override
    public Completer getCompleter() {
        return new CustomFileCompleter();
    }

    @Override
    public boolean match(String name) {
        return true;
    }

    @Override
    public String getName() {
        return tokenName;
    }

    @Override
    public boolean isMandatory() {
        return mandatory;
    }

    @Override
    public boolean isValueToken() {
        return false;
    }
}

