package com.jaspersoft.jasperserver.jrsh.core.script;

import com.jaspersoft.jasperserver.jrsh.core.evaluation.EvaluationStrategy;

import java.util.List;

public class SimpleScript implements Script {

    private Class<? extends EvaluationStrategy> strategyType;
    private List<String> source;

    public SimpleScript(Class<? extends EvaluationStrategy> strategyType, List<String> source) {
        this.strategyType = strategyType;
        this.source = source;
    }

    @Override
    public List<String> getSource() {
        return source;
    }

    @Override
    public Class<? extends EvaluationStrategy> getEvaluationStrategyType() {
        return strategyType;
    }
}
