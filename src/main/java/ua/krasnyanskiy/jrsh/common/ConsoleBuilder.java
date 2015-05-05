package ua.krasnyanskiy.jrsh.common;

import jline.console.ConsoleReader;
import jline.console.completer.AggregateCompleter;
import jline.console.completer.Completer;
import jline.console.completer.CompletionHandler;

import java.io.IOException;
import java.util.List;

public class ConsoleBuilder {

    private ConsoleReader console;

    public ConsoleBuilder() {
        try {
            this.console = new ConsoleReader();
        } catch (IOException e) {
            throw new RuntimeException("Cannot create JLine console.");
        }
    }

    public ConsoleBuilder withCompleters(List<Completer> completers) {
        console.addCompleter(new AggregateCompleter(completers));
        return this;
    }

    public ConsoleBuilder withPrompt(String prompt) {
        console.setPrompt(prompt);
        return this;
    }

    public ConsoleBuilder withHandler(CompletionHandler handler) {
        console.setCompletionHandler(handler);
        return this;
    }

    public ConsoleReader build() {
        return console;
    }
}
