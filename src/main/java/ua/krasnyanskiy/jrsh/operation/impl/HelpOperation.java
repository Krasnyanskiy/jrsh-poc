package ua.krasnyanskiy.jrsh.operation.impl;

import lombok.SneakyThrows;
import ua.krasnyanskiy.jrsh.operation.EvaluationResult;
import ua.krasnyanskiy.jrsh.operation.EvaluationResult.ResultCode;
import ua.krasnyanskiy.jrsh.operation.Operation;
import ua.krasnyanskiy.jrsh.operation.OperationFactory;
import ua.krasnyanskiy.jrsh.operation.grammar.Grammar;
import ua.krasnyanskiy.jrsh.operation.grammar.OperationGrammarFactory;
import ua.krasnyanskiy.jrsh.operation.parameter.AbstractOperationParameters;
import ua.krasnyanskiy.jrsh.operation.parameter.HelpOperationParameters;

import java.util.List;

public class HelpOperation implements Operation<HelpOperationParameters> {

    private HelpOperationParameters parameters;
    private Grammar grammar;

    @SneakyThrows
    public HelpOperation() {
        this.grammar = OperationGrammarFactory.getGrammar(getParametersType());
    }

    @Override
    public EvaluationResult eval() {
        StringBuilder builder = new StringBuilder();
        String context = parameters.getContext();

        if (context != null) {
            Operation op = OperationFactory.getOperation(context);
            builder.append(op.getDescription()).append("\n");
        } else {
            builder.append("\nUsage (Tool):   \u001B[37mjrsh\u001B[0m username%password@url <operation> <parameters>\n");
            builder.append("Usage (Shell):  \u001B[37mjrsh\u001B[0m username%password@url\n");
            builder.append("Usage (Script): \u001B[37mjrsh\u001B[0m script.jrs\n");
            builder.append("\nAvailable operations: \n");
            List<Operation<? extends AbstractOperationParameters>> operations = OperationFactory.getOperations();
            for (Operation op : operations) {
                builder.append(op.getDescription()).append("\n");
            }
        }
        return new EvaluationResult(builder.toString(), ResultCode.SUCCESS, this);
    }

    @Override
    public Grammar getGrammar() {
        return grammar;
    }

    @Override
    public String getDescription() {
        return "\t\u001B[1mHelp\u001B[0m shows information about JRSH and its operations." +
                "\n\tUsage: \u001B[37mhelp\u001B[0m <operation>";
    }

    @Override
    public Class<HelpOperationParameters> getParametersType() {
        return HelpOperationParameters.class;
    }

    @Override
    public void setOperationParameters(HelpOperationParameters parameters) {
        this.parameters = parameters;
    }
}
