package com.jaspersoft.jasperserver.jrsh.runner;

import com.jaspersoft.jasperserver.jrsh.core.common.ParameterConverter;
import com.jaspersoft.jasperserver.jrsh.core.common.Script;
import com.jaspersoft.jasperserver.jrsh.core.evaluation.strategy.EvaluationStrategy;
import lombok.extern.log4j.Log4j;

import static com.jaspersoft.jasperserver.jrsh.core.evaluation.strategy.EvaluationStrategyFactory.getStrategy;
import static com.jaspersoft.jasperserver.jrsh.core.evaluation.strategy.EvaluationStrategyTypeIdentifier.identifyType;

@Log4j
public class App {
    public static void main(String[] parameters) {
        /*
         * Get script data
         */
        Script script = ParameterConverter.convertToScript(parameters);
        Class<? extends EvaluationStrategy> strategyType = identifyType(parameters);
        /*
         * Get strategy & evaluate operation sequence
         */
        EvaluationStrategy strategy = getStrategy(strategyType);
        strategy.eval(script);
    }
}
