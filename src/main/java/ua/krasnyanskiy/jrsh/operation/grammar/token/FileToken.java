package ua.krasnyanskiy.jrsh.operation.grammar.token;

import jline.console.completer.Completer;
import ua.krasnyanskiy.jrsh.completion.CustomFileCompleter;

public class FileToken implements Token {

    private String tokenName = "filePath";
    private boolean mandatory;
    private boolean endPoint;

    public FileToken(String tokenName, boolean mandatory) {
        this.tokenName = tokenName;
        this.mandatory = mandatory;
    }

    public FileToken(String tokenName, boolean mandatory, boolean endPoint) {
        this.tokenName = tokenName;
        this.mandatory = mandatory;
        this.endPoint = endPoint;
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

    @Override
    public boolean isEndPoint() {
        return endPoint;
    }
}

