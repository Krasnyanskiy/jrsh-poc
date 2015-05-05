package ua.krasnyanskiy.jrsh;

import ua.krasnyanskiy.jrsh.evaluation.EvaluationStrategy;
import ua.krasnyanskiy.jrsh.evaluation.helper.StrategyHelper;
import ua.krasnyanskiy.jrsh.operation.parser.OperationParser;
import ua.krasnyanskiy.jrsh.operation.parser.OperationParserFacade;

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
        OperationParser operationParser = new OperationParserFacade();
        strategy.setOperationParser(operationParser);
        strategy.eval(args);
    }
}
