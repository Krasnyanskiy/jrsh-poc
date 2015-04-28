package ua.krasnyanskiy.jrsh.operation.grammar;

import jline.console.completer.Completer;
import ua.krasnyanskiy.jrsh.common.RepositoryCompleter;

public class RepositoryPathToken implements Token {

    private String tokenName = "repositoryPath";
    private boolean mandatory;

    public RepositoryPathToken(String tokenName, boolean mandatory) {
        this.tokenName = tokenName;
        this.mandatory = mandatory;
    }

    @Override
    public Completer getCompleter() {
        return new RepositoryCompleter();
        //return new EmptyCompleter();
    }

    @Override
    public boolean match(String name) {
        return /*name.startsWith("/") && (!name.endsWith("/"))*/true;
    }

    @Override
    public String getName() {
        return tokenName;
    }

    @Override
    public boolean isMandatory() {
        return mandatory;
    }
}

