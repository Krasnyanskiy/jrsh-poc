package ua.krasnyanskiy.jrsh.operation.grammar.token;

import jline.console.completer.Completer;
import lombok.EqualsAndHashCode;
import ua.krasnyanskiy.jrsh.completion.CustomFileCompleter;

@EqualsAndHashCode(exclude = {"mandatory", "terminal"})
public class FileToken implements Token {

    private String name = "filePath";
    //private String value;
    private boolean mandatory;
    private boolean terminal;

    public FileToken(String name, boolean mandatory) {
        this.name = name;
        this.mandatory = mandatory;
    }

    public FileToken(String name, boolean mandatory, boolean terminal) {
        this.name = name;
        this.mandatory = mandatory;
        this.terminal = terminal;
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
        return name;
    }

    @Override
    public String getValue() {
        //return value;
        return "";
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
    public boolean isTerminal() {
        return terminal;
    }
}

