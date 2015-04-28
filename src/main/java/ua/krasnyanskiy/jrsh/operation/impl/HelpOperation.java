package ua.krasnyanskiy.jrsh.operation.impl;

import jline.console.ConsoleReader;
import lombok.NonNull;
import ua.krasnyanskiy.jrsh.operation.grammar.Grammar;
import ua.krasnyanskiy.jrsh.operation.Operation;
import ua.krasnyanskiy.jrsh.operation.Parameters;

import java.util.concurrent.Callable;

public class HelpOperation implements Operation {

    private ConsoleReader console;

    @Override
    public Callable<Integer> perform(@NonNull Parameters parameters) {
        return new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return 2;
            }
        };
    }

    @Override
    public Grammar getGrammar() {
        return null;
    }

    @Override
    public Parameters getParameters(String... args) {
        return null;
    }

    @Override
    public String getDescription() {
        return "This is Help";
    }

    @Override
    public void setConsole(ConsoleReader console) {
        this.console = console;
    }
}
