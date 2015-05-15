package refactoring.impl.strategy;

import refactoring.EvaluationStrategy;
import refactoring.OperationParser;
import refactoring.impl.CompleterFactory;
import ua.krasnyanskiy.jrsh.common.ConsoleBuilder;

import java.io.IOException;

public class EvaluationStrategyFactory {

    private final OperationParser parser;
    private final ConsoleBuilder builder;
    private final CompleterFactory factory;

    public EvaluationStrategyFactory(OperationParser parser, ConsoleBuilder builder, CompleterFactory factory) {
        this.parser = parser;
        this.builder = builder;
        this.factory = factory;
    }

    public EvaluationStrategy createStrategy(String[] appArguments) throws IOException {
        EvaluationStrategy strategy;
        switch (appArguments.length) {
            case 0:
            case 1:
                strategy = new ToolEvaluationStrategy(appArguments, parser, builder);
                break;
            case 2:
                strategy = new ShellEvaluationStrategy(appArguments, parser, builder, factory);
                break;
            default:
                strategy = new ScriptEvaluationStrategy(appArguments, parser, builder);
                break;
        }
        return strategy;
    }
}
