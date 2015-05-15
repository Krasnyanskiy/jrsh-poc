package ua.krasnyanskiy.jrsh;

import ua.krasnyanskiy.jrsh.evaluation.EvaluationStrategy;
import ua.krasnyanskiy.jrsh.evaluation.StrategyHelper;

/**
 * Main class of the application. It defines the finest
 * strategy regarding to the app arguments and delegates
 * evaluation to the strategy.
 *
 * @author Alexander Krasnyanskiy
 * @since 1.0
 */
public class App {
    public static void main(String[] appArgs) throws Exception {
        EvaluationStrategy strategy = StrategyHelper.define(appArgs);
        strategy.eval(appArgs);
    }
}
