package com.jaspersoft.jasperserver.jrsh.runner;

import com.jaspersoft.jasperserver.jrsh.core.common.ArgumentConverter;
import com.jaspersoft.jasperserver.jrsh.core.common.Script;
import com.jaspersoft.jasperserver.jrsh.core.evaluation.strategy.EvaluationStrategy;
import lombok.extern.log4j.Log4j;

import static com.jaspersoft.jasperserver.jrsh.core.evaluation.strategy.EvaluationStrategyFactory.getStrategy;
import static com.jaspersoft.jasperserver.jrsh.core.evaluation.strategy.EvaluationStrategyTypeIdentifier.identifyType;

@Log4j
public class App {
    public static void main(String[] args) {
        //
        // Get script data
        //
        Script script = ArgumentConverter.convertToScript(args);
        Class<? extends EvaluationStrategy> strategyType = identifyType(args);
        //
        // Get strategy & evaluate operation sequence
        //
        EvaluationStrategy strategy = getStrategy(strategyType);
        strategy.eval(script);
    }
}
