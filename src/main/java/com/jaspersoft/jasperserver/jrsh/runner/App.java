package com.jaspersoft.jasperserver.jrsh.runner;

import com.jaspersoft.jasperserver.jrsh.core.Evaluator;

public class App {
    public static void main(String[] parameters) {
        Evaluator evaluator = new Evaluator(parameters);
        evaluator.eval();
    }
}
