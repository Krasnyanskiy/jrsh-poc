package com.jaspersoft.jasperserver.jrsh.core;

import com.jaspersoft.jasperserver.jrsh.core.evaluation.EvaluationStrategy;
import com.jaspersoft.jasperserver.jrsh.core.evaluation.EvaluationStrategyFactory;
import com.jaspersoft.jasperserver.jrsh.core.operation.OperationResult;
import com.jaspersoft.jasperserver.jrsh.core.script.Script;
import com.jaspersoft.jasperserver.jrsh.core.script.ScriptBuilder;
import lombok.NonNull;

/**
 * A core evaluation class.
 *
 * @author Alexander Krasnyanskiy
 * @version 1.0
 */
@SuppressWarnings("unchecked") public class Evaluator {

    private Script script;
    private EvaluationStrategy strategy;

    /**
     * This main constructor defines a proper script
     * and based on the script sets the proper
     * evaluation strategy.
     *
     * @param parameters app parameters
     */
    public Evaluator(String[] parameters) {
        this.script = new ScriptBuilder(parameters).build();
        this.strategy = EvaluationStrategyFactory.getStrategy(script.getClass());
    }

    /**
     * Evaluate script and return result.
     *
     * @return operation sequence result
     */
    @NonNull public OperationResult eval() {
        return strategy.eval(script);
    }
}
