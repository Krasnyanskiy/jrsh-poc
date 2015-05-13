package ua.krasnyanskiy.jrsh.operation.parser;

/**
 * @author Alexander Krasnyanskiy
 * @since 1.0
 */
public interface OperationParametersParser {

    <P extends OperationParameters> P parse(String line);

}