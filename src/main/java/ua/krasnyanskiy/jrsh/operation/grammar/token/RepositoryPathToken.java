package ua.krasnyanskiy.jrsh.operation.grammar.token;

import jline.console.completer.Completer;
import ua.krasnyanskiy.jrsh.completion.StaticRepositoryCompleter;

public class RepositoryPathToken implements Token {

    private String tokenName = "repositoryPath";
    private boolean mandatory;
    private boolean endPoint;

    public RepositoryPathToken(String tokenName, boolean mandatory) {
        this.tokenName = tokenName;
        this.mandatory = mandatory;
    }

    public RepositoryPathToken(String tknName, boolean mandatory, boolean endPoint) {
        this.endPoint = endPoint;
        this.tokenName = tknName;
        this.mandatory = mandatory;
    }

    @Override
    public Completer getCompleter() {
        //return new RepositoryCompleter();
        //return new EmptyCompleter();
        //return new MockRepositoryCompleter();
        return new StaticRepositoryCompleter();
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

    @Override
    public boolean isValueToken() {
        return false;
    }

    @Override
    public boolean isEndPoint() {
        return endPoint;
    }
}

