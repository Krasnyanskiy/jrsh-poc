package ua.krasnyanskiy.jrsh.operation.impl;

import jline.console.ConsoleReader;
import ua.krasnyanskiy.jrsh.operation.Operation;
import ua.krasnyanskiy.jrsh.operation.grammar.Grammar;
import ua.krasnyanskiy.jrsh.operation.parameter.HelpOperationParameters;

import java.util.concurrent.Callable;

public class HelpOperation implements Operation<HelpOperationParameters> {

    private HelpOperationParameters parameters;
    private Grammar grammer;

    @Override
    public Callable<String> execute() {
        return new Callable<String>() {
            @Override
            public String call() throws Exception {
                return "abc";
            }
        };
    }

    @Override
    public Grammar getGrammar() {
        return grammer;
    }

    @Override
    public String getDescription() {
        return "This is Help Operation";
    }

    @Override
    public Class<HelpOperationParameters> getParametersType() {
        return HelpOperationParameters.class;
    }

    @Override
    public void setOperationParameters(HelpOperationParameters parameters) {
        this.parameters = parameters;
    }

    @Override
    public void setConsole(ConsoleReader console) {
        // ignored
    }
}
