package ua.krasnyanskiy.jrsh.exception;

public class WrongParametersFormatException extends ParseOperationException {
    public WrongParametersFormatException(String fieldName) {
        super(String.format("Cannot find setter for the field (%s)", fieldName));
    }
}
