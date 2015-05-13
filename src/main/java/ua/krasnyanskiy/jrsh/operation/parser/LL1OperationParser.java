package ua.krasnyanskiy.jrsh.operation.parser;

@SuppressWarnings("unchecked")
public class LL1OperationParser implements OperationParametersParser {

    @Override
    public <P extends OperationParameters> P parse(String line) {
//        String[] tokens = line.split("\\s+");
//        String operationName = tokens[0];
//        Operation<AbstractOperationParameters> operation = (Operation<AbstractOperationParameters>) OperationFactory.getOperation(operationName);
//
//        if (operation == null) {
//            throw new NoSuchOperationException(operationName);
//        }
//
//        AbstractOperationParameters parameters = getOperationParameters(operation, tokens);
//        operation.setOperationParameters(parameters);
//        return operation;
        return null;
    }

//    @SneakyThrows
//    protected AbstractOperationParameters getOperationParameters(Operation operation, String[] tokenValues) {
//        AbstractOperationParameters parameters = null;
//
//        //Grammar grammar = operation.getGrammar();
//        //Collection<Rule> rules = grammar.getRules();
//
//        for (Rule rule : rules) {
//            List<Token> ruleTokens = rule.getTokens();
//            boolean isMatchingRule = true;
//            if (tokenValues.length == ruleTokens.size()) {
//                for (int i = 0; i < ruleTokens.size(); i++) {
//                    if (!ruleTokens.get(i).match(tokenValues[i])) {
//                        isMatchingRule = false;
//                        break;
//                    }
//                }
//                if (isMatchingRule) {
//                    if (parameters == null) {
//                        parameters = (AbstractOperationParameters) operation.getParametersType().newInstance();
//                    }
//                    Reflection.setParametersValues(parameters, ruleTokens, tokenValues);
//                }
//            }
//        }
//        if (parameters == null) {
//            throw new ParseParametersException(format(ERROR_MSG, tokenValues[0]));
//        }
//        return parameters;
//    }
}
