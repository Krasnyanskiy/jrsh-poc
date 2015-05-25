package com.jaspersoft.jasperserver.jrsh.core.evaluation;

public class WrongStrategyTypeException extends RuntimeException {
    public WrongStrategyTypeException(Class<? extends EvaluationStrategy> strategyType) {
        super("Cannot create a strategy of type: " + strategyType);
    }
}
