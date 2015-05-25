package com.jaspersoft.jasperserver.jrsh.core;

import com.jaspersoft.jasperserver.jrsh.core.evaluation.EvaluationStrategy;
import com.jaspersoft.jasperserver.jrsh.core.evaluation.EvaluationStrategyFactory;
import com.jaspersoft.jasperserver.jrsh.core.operation.OperationResult;
import com.jaspersoft.jasperserver.jrsh.core.script.Script;
import com.jaspersoft.jasperserver.jrsh.core.script.ScriptBuilder;

public class Evaluator {

    private Script script;
    private EvaluationStrategy strategy;

    public Evaluator(String[] parameters) {
        this.script = new ScriptBuilder(parameters).build();
        this.strategy = EvaluationStrategyFactory.getStrategy(script.getEvaluationStrategyType());
    }

    public OperationResult eval() {
        return strategy.eval(script);
    }
}
