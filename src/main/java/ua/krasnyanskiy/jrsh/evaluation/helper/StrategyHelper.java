package ua.krasnyanskiy.jrsh.evaluation.helper;

import lombok.NonNull;
import ua.krasnyanskiy.jrsh.evaluation.EvaluationStrategy;
import ua.krasnyanskiy.jrsh.evaluation.ScriptEvaluationStrategy;
import ua.krasnyanskiy.jrsh.evaluation.ShellEvaluationStrategy;
import ua.krasnyanskiy.jrsh.evaluation.ToolEvaluationStrategy;

import java.io.IOException;

import static ua.krasnyanskiy.jrsh.operation.grammar.token.TokenPreconditions.isLoginToken;
import static ua.krasnyanskiy.jrsh.operation.grammar.token.TokenPreconditions.isScriptNameToken;

/**
 * Strategy Helper defines the appropriate {@link EvaluationStrategy}.
 *
 * @author Alexander Krasnyanskiy
 * @since 1.0
 */
public class StrategyHelper {

    /**
     * Parses tokens and return appropriate strategy.
     *
     * @param args tokens
     * @return strategy
     */
    public static EvaluationStrategy define(@NonNull String[] args) throws IOException {
        EvaluationStrategy strategy;
        switch (args.length) {

            case 0: {
                strategy = new ToolEvaluationStrategy();
                break;
            }

            case 1: {
                if (isLoginToken(args[0])) {
                    strategy = new ShellEvaluationStrategy();
                    break;
                }
                if (isScriptNameToken(args[0])) {
                    strategy = new ScriptEvaluationStrategy();
                    break;
                }
                strategy = new ToolEvaluationStrategy();
                break;
            }

            case 2: {
                if ("--script".equals(args[0]) || "eval".equals(args[0])
                        && isScriptNameToken(args[1])) {
                    strategy = new ScriptEvaluationStrategy();
                } else {
                    strategy = new ToolEvaluationStrategy();
                }
                break;
            }

            default: {
                strategy = new ToolEvaluationStrategy();
            }
        }
        return strategy;
    }
}
