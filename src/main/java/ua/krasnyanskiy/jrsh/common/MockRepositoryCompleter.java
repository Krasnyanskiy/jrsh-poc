package ua.krasnyanskiy.jrsh.common;

import jline.console.completer.Completer;

import java.util.List;

public class MockRepositoryCompleter implements Completer {

    @Override
    public int complete(String buffer, int cursor, List<CharSequence> candidates) {
        if (buffer == null) return cursor;
        candidates.add("/public/Samples/Reports/08.UnitSalesDetailReport");
        return cursor > buffer.length() ? cursor : buffer.length() + 1;
    }

}
