package ua.krasnyanskiy.jrsh.completion;

import jline.console.completer.Completer;

import java.util.List;

public class StaticRepositoryCompleter implements Completer {
    @Override
    public int complete(String buffer, int cursor, List<CharSequence> candidates) {
        return 0;
    }
}
