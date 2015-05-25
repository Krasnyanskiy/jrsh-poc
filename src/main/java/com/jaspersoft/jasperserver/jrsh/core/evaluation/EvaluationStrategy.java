package com.jaspersoft.jasperserver.jrsh.core.evaluation;

import com.jaspersoft.jasperserver.jrsh.core.operation.OperationResult;
import com.jaspersoft.jasperserver.jrsh.core.script.Script;

public interface EvaluationStrategy {

    OperationResult eval(Script script);

}