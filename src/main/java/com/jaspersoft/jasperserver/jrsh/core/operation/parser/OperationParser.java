package com.jaspersoft.jasperserver.jrsh.core.operation.parser;

import com.jaspersoft.jasperserver.jrsh.core.operation.Operation;
import com.jaspersoft.jasperserver.jrsh.core.operation.parser.exception.OperationParseException;

/**
 * An operation parser.
 *
 * @author Alexander Krasnyanskiy
 * @version 1.0
 */
public interface OperationParser {

    /**
     * Parses a text input into the concrete {@link Operation}
     * instance. May also produce {@link OperationParseException} if
     * there is no matched operation type or if operation
     * parameters are incorrect.
     *
     * @param line input
     * @return operation
     * @throws OperationParseException
     */
    Operation parse(String line) throws OperationParseException;

}