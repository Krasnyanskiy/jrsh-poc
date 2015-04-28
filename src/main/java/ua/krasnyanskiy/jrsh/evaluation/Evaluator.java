package ua.krasnyanskiy.jrsh.evaluation;

import lombok.NonNull;
import ua.krasnyanskiy.jrsh.evaluation.strategy.EvaluationStrategy;
import ua.krasnyanskiy.jrsh.evaluation.strategy.StrategyHelper;

/**
 * @author Alexander Krasnyanskiy
 * @since 1.0
 */
public class Evaluator {

    private StrategyHelper helper = new StrategyHelper();

    public void eval(@NonNull String... args) {
        EvaluationStrategy strategy = helper.define(args);
        strategy.eval(args);
    }

}