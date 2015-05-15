package ua.krasnyanskiy.jrsh.evaluation;

import lombok.NonNull;
import ua.krasnyanskiy.jrsh.operation.grammar.token.TokenPreconditions;

import java.io.IOException;

import static ua.krasnyanskiy.jrsh.operation.grammar.token.TokenPreconditions.isScriptNameToken;

/**
 * Strategy Helper defines the appropriate {@link EvaluationStrategy}.
 *
 * @author Alexander Krasnyanskiy
 * @since 1.0
 */
public class StrategyHelper {
    public static EvaluationStrategy define(@NonNull String[] appArgs) throws IOException {
        EvaluationStrategy strategy = null;
        switch (appArgs.length) {
//            case 0:
//                strategy = new ToolEvaluationStrategy();
//                break;
            case 1:
                if (TokenPreconditions.isConnectionStringToken(appArgs[0])) {
                    strategy = new ShellEvaluationStrategy();
                    break;
                }
                if (isScriptNameToken(appArgs[0])) {
                    strategy = new ScriptEvaluationStrategy();
                    break;
                }
//                strategy = new ToolEvaluationStrategy();
//                break;
//            case 2:
//                if ("--script".equals(appArgs[0]) && isScriptNameToken(appArgs[1])) {
//                    strategy = new ScriptEvaluationStrategy();
//                } else {
//                    strategy = new ToolEvaluationStrategy();
//                }
//                break;
//            default:
//                strategy = new ToolEvaluationStrategy();
        }
        return strategy;
    }
}