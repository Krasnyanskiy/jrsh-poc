package ua.krasnyanskiy.jrsh.common;

import static java.lang.String.format;

public class NoSuchOperationException extends ParseOperationException {
    public NoSuchOperationException(String name) {
        super(format("No such operation (%s)", name));
    }
}
