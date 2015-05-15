//package ua.krasnyanskiy.jrsh.evaluation;
//
//import lombok.NonNull;
//import org.apache.commons.lang3.StringUtils;
//import ua.krasnyanskiy.jrsh.common.ConsoleBuilder;
//import ua.krasnyanskiy.jrsh.operation.OperationResult;
//import ua.krasnyanskiy.jrsh.operation.OperationResult.ResultCode;
//import ua.krasnyanskiy.jrsh.operation.OperationFactory;
//import ua.krasnyanskiy.jrsh.operation.grammar.token.TokenPreconditions;
//
//import java.io.IOException;
//import java.util.Arrays;
//
//import static java.lang.System.exit;
//import static ua.krasnyanskiy.jrsh.common.Separator.WHITE_SPACE;
//
///**
// * @author Alexander Krasnyanskiy
// * @since 1.0
// */
//public class ToolEvaluationStrategy extends AbstractEvaluationStrategy {
//
//    public ToolEvaluationStrategy() {
//        super.console = new ConsoleBuilder().build();
//    }
//
//    public void eval(@NonNull String[] appArgs) throws IOException {
//        OperationResult res;
//        String operation;
//
//        switch (appArgs.length) {
//            case 0:
//                res = OperationFactory.getOperation("help").eval();
//                break;
//            case 1:
//                res = parseParameters(appArgs[0]);
//                break;
//            default:
//                operation = appArgs[0];
//                if (TokenPreconditions.isConnectionStringToken(operation)) {
//                    operation = "login ".concat(operation);
//                    res = parseParameters(operation);
//                    if (res.getCode() == ResultCode.FAILED) {
//                        console.println(res.getMessage());
//                        console.flush();
//                        exit(1);
//                    }
//                    String[] __args = Arrays.copyOfRange(appArgs, 1, appArgs.length);
//                    operation = StringUtils.join(__args, WHITE_SPACE);
//                } else {
//                    operation = StringUtils.join(appArgs, WHITE_SPACE);
//                }
//                res = parseParameters(operation);
//        }
//
//        console.println(res.getMessage());
//        console.flush();
//    }
//}
