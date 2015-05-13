package ua.krasnyanskiy.jrsh.operation.impl;


import ua.krasnyanskiy.jrsh.operation.EvaluationResult;
import ua.krasnyanskiy.jrsh.operation.Operation;
import ua.krasnyanskiy.jrsh.operation.parser.OperationParameters;

public class FakeOperation implements Operation<FakeOperationParameters> {

    @Override
    public EvaluationResult eval(FakeOperationParameters parameters) {
        return null;
    }
}

class FakeOperationParameters implements OperationParameters {

}
