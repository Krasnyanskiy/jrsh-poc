package com.jaspersoft.jasperserver.jrsh.core.evaluation.strategy;

import com.jaspersoft.jasperserver.jrsh.core.evaluation.WrongStrategyTypeException;
import com.jaspersoft.jasperserver.jrsh.core.evaluation.strategy.EvaluationStrategy;

public class EvaluationStrategyFactory {
    public static EvaluationStrategy getStrategy(Class<? extends EvaluationStrategy> strategyType) {
        try {
            return strategyType.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new WrongStrategyTypeException(strategyType);
        }
    }
}
