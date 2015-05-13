package ua.krasnyanskiy.jrsh;

import ua.krasnyanskiy.jrsh.evaluation.EvaluationStrategy;
import ua.krasnyanskiy.jrsh.evaluation.helper.StrategyHelper;
import ua.krasnyanskiy.jrsh.operation.parser.LL1OperationParser;
import ua.krasnyanskiy.jrsh.operation.parser.OperationParametersParser;

/**
 * Main class of the application. It defines the finest
 * strategy regarding to the app arguments and delegates
 * evaluation to the strategy.
 *
 * @author Alexander Krasnyanskiy
 * @since 1.0
 */
public class App {
    public static void main(String[] args) throws Exception {
        EvaluationStrategy strategy = StrategyHelper.define(args);
        OperationParametersParser operationParser = new LL1OperationParser();
        strategy.setOperationParser(operationParser);
        strategy.eval(args);
    }
}
