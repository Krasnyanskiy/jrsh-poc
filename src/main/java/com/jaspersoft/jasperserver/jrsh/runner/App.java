package com.jaspersoft.jasperserver.jrsh.runner;

import com.jaspersoft.jasperserver.jrsh.core.Evaluator;
import com.jaspersoft.jasperserver.jrsh.core.operation.OperationResult;

public class App {
    public static void main(String[] parameters) {
        Evaluator evaluator = new Evaluator(parameters);
        OperationResult result = evaluator.eval();
        System.out.println(result.getResultMessage());
    }
}
