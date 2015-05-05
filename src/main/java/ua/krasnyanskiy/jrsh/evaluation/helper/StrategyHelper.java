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
     * @param tokens tokens
     * @return strategy
     */
    public static EvaluationStrategy define(@NonNull String... tokens) throws IOException {
        EvaluationStrategy strategy;
        switch (tokens.length) {

            case 0: {
                strategy = new ToolEvaluationStrategy();
                break;
            }

            case 1: {
                if (isLoginToken(tokens[0])) {
                    strategy = new ShellEvaluationStrategy();
                    break;
                }
                if (isScriptNameToken(tokens[0])) {
                    strategy = new ScriptEvaluationStrategy();
                    break;
                }
                strategy = new ToolEvaluationStrategy();
                break;
            }

            case 2: {
                if ("--script".equals(tokens[0]) || "eval".equals(tokens[0])
                        && isScriptNameToken(tokens[1])) {
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
