package ua.krasnyanskiy.jrsh.evaluation.strategy;

import lombok.NonNull;

import static ua.krasnyanskiy.jrsh.common.TokenUtil.isLoginToken;
import static ua.krasnyanskiy.jrsh.common.TokenUtil.isScriptNameToken;

/**
 * Helper defines the type of strategy.
 *
 * @author Alexander Krasnyanskiy
 * @since 1.0
 */
public class StrategyHelper {

    private static final EvaluationStrategy TOOL = new ToolEvaluationStrategy();
    private static final EvaluationStrategy SHELL = new ShellEvaluationStrategy();
    private static final EvaluationStrategy SCRIPT = new ScriptEvaluationStrategy();

    public EvaluationStrategy define(@NonNull String... chunks) {

        switch (chunks.length) {
            case 0: {
                return TOOL;
            }
            case 1: {
                if (isLoginToken(chunks[0])) {
                    return SHELL;
                }
                if (isScriptNameToken(chunks[0])) {
                    return SCRIPT;
                }
                return TOOL;
            }
            case 2: {
                return ("--script".equals(chunks[0]) || "eval".equals(chunks[0]))
                        && isScriptNameToken(chunks[1]) ? SCRIPT : TOOL;
            }
            default: {
                return TOOL;
            }
        }
    }
}
