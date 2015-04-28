package ua.krasnyanskiy.jrsh.common;

import jline.console.completer.Completer;

import java.util.List;

/**
 * Used for completer sequence connectivity.
 */
public class EmptyCompleter implements Completer {
    @Override
    public int complete(String buffer, int cursor, List<CharSequence> candidates) {
        if (buffer == null) return cursor;
        candidates.add(" "); // Whatever input you enter, it will be complimented by a whitespace
        return cursor > buffer.length() ? cursor : buffer.length() + 1;
    }
}
