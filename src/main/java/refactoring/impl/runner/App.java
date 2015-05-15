package refactoring.impl.runner;

import refactoring.EvaluationStrategy;
import refactoring.OperationParser;
import refactoring.impl.CompleterFactory;
import refactoring.impl.Lexer;
import refactoring.impl.OperationParserImpl;
import refactoring.impl.strategy.EvaluationStrategyFactory;
import ua.krasnyanskiy.jrsh.common.ConsoleBuilder;

/**
 * Main class of the application. It defines the finest
 * strategy regarding to the app arguments and delegates
 * evaluation to the strategy.
 *
 * @author Alexander Krasnyaskiy
 * @version 1.0
 */
public class App {
    public static void main(String[] args) throws Exception {
        //
        // Prepare modules
        //
        OperationParser oParser = new OperationParserImpl(new Lexer());
        ConsoleBuilder cBuilder = new ConsoleBuilder();
        CompleterFactory cFactory = new CompleterFactory();
        //
        // Define evaluation algorithm
        //
        EvaluationStrategyFactory sFactory = new EvaluationStrategyFactory(oParser, cBuilder, cFactory);
        EvaluationStrategy strategy = sFactory.createStrategy(args);
        //
        // Start evaluation
        //
        strategy.eval();

    }
}
