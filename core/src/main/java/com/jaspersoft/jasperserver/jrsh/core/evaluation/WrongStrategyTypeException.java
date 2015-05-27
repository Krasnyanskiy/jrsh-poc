package com.jaspersoft.jasperserver.jrsh.core.evaluation;

import com.jaspersoft.jasperserver.jrsh.core.evaluation.strategy.EvaluationStrategy;

public class WrongStrategyTypeException extends RuntimeException {
    public WrongStrategyTypeException(Class<? extends EvaluationStrategy> strategyType) {
        super("Cannot create a strategy of type: " + strategyType);
    }
}
