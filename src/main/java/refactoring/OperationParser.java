package refactoring;

import refactoring.impl.ParseException;

public interface OperationParser {

    /**
     * Parse text input into the concrete {@link Operation}
     * instance. May also produce {@link ParseException} if
     * there is no matched operation type or if operation
     * parameters are incorrect.
     * <p/>
     * The format of every line is as follows:
     * [operation][parameters]
     * <p/>
     * Example:
     * $> login joe%secret@localhost
     *
     * @param line user input
     * @return operation
     * @throws ParseException
     */
    Operation parse(String line) throws ParseException;

}
