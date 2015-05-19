package com.jaspersoft.jasperserver.jrsh.core.operation;

import lombok.NonNull;

/**
 * Base interface for all operations in the app. It
 * should contain a static metadata which intended
 * for grammar parsing and for configuring completion.
 *
 * @author Alexander Krasnyanskiy
 * @version 1.0
 */
public interface Operation {

    /**
     * Evaluates the operation and return the
     * result of evaluating.
     *
     * @return result
     */
    @NonNull OperationResult eval();

}