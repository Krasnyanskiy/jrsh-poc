package com.jaspersoft.jasperserver.jrsh.runner;

import com.jaspersoft.jasperserver.jrsh.core.evaluation.EvaluationStrategy;
import com.jaspersoft.jasperserver.jrsh.core.operation.OperationResult;
import com.jaspersoft.jasperserver.jrsh.core.script.Script;
import com.jaspersoft.jasperserver.jrsh.core.script.ScriptConverter;
import lombok.extern.log4j.Log4j;

import static com.jaspersoft.jasperserver.jrsh.core.evaluation.EvaluationStrategyFactory.getStrategy;
import static com.jaspersoft.jasperserver.jrsh.core.evaluation.EvaluationStrategyTypeIdentifier.identifyType;

@Log4j
public class App {
    public static void main(String[] parameters) {
        /*
         * Get script data
         */
        Script script = ScriptConverter.convertToScript(parameters);
        Class<? extends EvaluationStrategy> strategyType = identifyType(parameters);
        /*
         * Get strategy & evaluate operation sequence
         */
        EvaluationStrategy strategy = getStrategy(strategyType);
        OperationResult result = strategy.eval(script);

        log(result);
    }

    private static void log(OperationResult result) {
        StringBuilder builder = new StringBuilder();
        builder.append(result.getResultMessage()).append("\n");
        while (result != null) {
            result = result.getPrevious();
            if (result != null) {
                builder.append(result.getResultMessage()).append("\n");
            }
        }
        log.info(builder.toString());
    }
}
