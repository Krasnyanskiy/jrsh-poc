package com.jaspersoft.jasperserver.jrsh.core.evaluation;

import com.jaspersoft.jasperserver.jrsh.core.evaluation.impl.ScriptEvaluationStrategy;
import com.jaspersoft.jasperserver.jrsh.core.evaluation.impl.ShellEvaluationStrategy;
import com.jaspersoft.jasperserver.jrsh.core.evaluation.impl.ToolEvaluationStrategy;

import static com.jaspersoft.jasperserver.jrsh.core.operation.grammar.token.TokenPreconditions.isConnectionString;
import static com.jaspersoft.jasperserver.jrsh.core.operation.grammar.token.TokenPreconditions.isScriptFileName;

/**
 * Helper class that identifies a suitable strategy
 * for the operation processing.
 *
 * @author Alexander Krasnyanskiy
 * @version 1.0
 */
public class EvaluationStrategyTypeIdentifier {

    /**
     * Identifies a suitable strategy
     *
     * @param parameters initial app arguments
     * @return strategy
     */
    public static Class<? extends EvaluationStrategy> identifyType(String[] parameters) {
        Class<? extends EvaluationStrategy> strategyType;
        if (parameters.length == 1 && isConnectionString(parameters[0])) {
            strategyType = ShellEvaluationStrategy.class;
        } else if (parameters.length == 2 && "--script".equals(parameters[0])
                && isScriptFileName(parameters[1])) {
            strategyType = ScriptEvaluationStrategy.class;
        } else {
            strategyType = ToolEvaluationStrategy.class;
        }
        return strategyType;
    }
}
