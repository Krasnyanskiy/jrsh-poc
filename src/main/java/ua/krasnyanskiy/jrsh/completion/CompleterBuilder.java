//package ua.krasnyanskiy.jrsh.completion;
//
//import jline.console.completer.AggregateCompleter;
//import jline.console.completer.ArgumentCompleter;
//import jline.console.completer.Completer;
//import jline.console.completer.NullCompleter;
//import ua.krasnyanskiy.jrsh.operation.grammar.Rule;
//import ua.krasnyanskiy.jrsh.operation.grammar.token.Token;
//
//import java.util.Collection;
//import java.util.List;
//
///**
// * Used for building the completer based on operation rules.
// *
// * @author Alexander Krasnyanskiy
// * @since 1.0
// */
//public class CompleterBuilder {
//
//    private AggregateCompleter operationCmt;
//
//    public CompleterBuilder() {
//        this.operationCmt = new AggregateCompleter();
//    }
//
//    /**
//     * Adds single rule to operation completer.
//     *
//     * @param rule operation rule
//     * @return $this
//     */
//    public CompleterBuilder withRule(Rule rule) {
//        ArgumentCompleter ruleCmt = new ArgumentCompleter();
//        List<Token> tokens = rule.getTokens();
//        for (Token tkn : tokens) {
//            Completer cmt = tkn.getCompleter();
//            ruleCmt.getCompleters().add(cmt);
//        }
//        ruleCmt.getCompleters().add(new NullCompleter());
//        operationCmt.getCompleters().add(ruleCmt);
//        return this;
//    }
//
//    /**
//     * Adds rules to operation completer.
//     *
//     * @param rules operation rules
//     * @return $this
//     */
//    public CompleterBuilder withRules(Collection<Rule> rules) {
//        ArgumentCompleter ruleCmt = new ArgumentCompleter();
//        for (Rule rule : rules) {
//            List<Token> tokens = rule.getTokens();
//            for (Token tkn : tokens) {
//                Completer cmt = tkn.getCompleter();
//                ruleCmt.getCompleters().add(cmt);
//            }
//            ruleCmt.getCompleters().add(new NullCompleter());
//            operationCmt.getCompleters().add(ruleCmt);
//            ruleCmt = new ArgumentCompleter();
//        }
//        return this;
//    }
//
//    public Completer build() {
//        return operationCmt;
//    }
//}
