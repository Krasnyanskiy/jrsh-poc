package ua.krasnyanskiy.jrsh.completion.completer;

import jline.console.completer.Completer;

import java.util.List;

public class RepositoryMockCompleter implements Completer {

    @Override
    public int complete(String buffer, int cursor, List<CharSequence> candidates) {
        if (buffer == null) {
            candidates.add("/public/Samples/Reports/08.UnitSalesDetailReport");
            return cursor;
        }
        candidates.add(" ");
        return cursor > buffer.length() ? cursor : buffer.length() + 1;
    }

}
