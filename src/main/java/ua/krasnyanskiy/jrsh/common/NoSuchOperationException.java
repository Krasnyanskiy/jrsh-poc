package ua.krasnyanskiy.jrsh.common;

public class NoSuchOperationException extends RuntimeException {

    public NoSuchOperationException() {
        super("No such operation");
    }
}
