package com.jaspersoft.jasperserver.jrsh.core.evaluation;

public class EvaluationStrategyFactory {
    public static EvaluationStrategy getStrategy(Class<? extends EvaluationStrategy> strategyType) {
        try {
            return strategyType.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new WrongStrategyTypeException(strategyType);
        }
    }
}
