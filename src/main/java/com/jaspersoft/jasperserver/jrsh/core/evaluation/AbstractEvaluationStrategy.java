package com.jaspersoft.jasperserver.jrsh.core.evaluation;

import com.jaspersoft.jasperserver.jrsh.core.operation.parser.LL1OperationParser;
import com.jaspersoft.jasperserver.jrsh.core.operation.parser.OperationParser;
import com.jaspersoft.jasperserver.jrsh.core.script.Script;

public abstract class AbstractEvaluationStrategy<T extends Script> implements EvaluationStrategy<T> {

    protected OperationParser parser;

    public AbstractEvaluationStrategy() {
        this.parser = new LL1OperationParser();
    }

}
