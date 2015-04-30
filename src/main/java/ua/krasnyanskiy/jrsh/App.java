package ua.krasnyanskiy.jrsh;

import ua.krasnyanskiy.jrsh.evaluation.EvaluationStrategy;
import ua.krasnyanskiy.jrsh.evaluation.helper.StrategyHelper;
import ua.krasnyanskiy.jrsh.operation.parameter.OperationParser;

/**
 * Main class of the application. It defines the finest strategy regarding to the app arguments
 * and delegates evaluation to the strategy.
 *
 * @author Alexander Krasnyanskiy
 * @since 1.0
 */
public class App {
    public static void main(String[] args) throws Exception {

        StrategyHelper helper = new StrategyHelper();
        OperationParser parser = new OperationParser();

        EvaluationStrategy strategy = helper.define(args);
        strategy.setOperationParser(parser);
        strategy.eval(args);
    }
}
