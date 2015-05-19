package com.jaspersoft.jasperserver.jrsh.core.evaluation;

import com.jaspersoft.jasperserver.jrsh.core.operation.OperationResult;
import com.jaspersoft.jasperserver.jrsh.core.script.Script;
import lombok.NonNull;

/**
 * All plug-in execution strategy classes should implement
 * this interface. Strategy is responsible for operation
 * evaluation and handling the result.
 *
 * @author Alexander Krasnyanskiy
 * @since 1.0
 */
public interface EvaluationStrategy<ScriptType extends Script> {

    /**
     * Evaluates the algorithm logic.
     *
     * @param script initial user input
     */
    @NonNull OperationResult eval(ScriptType script);

}