package com.jaspersoft.jasperserver.jrsh.core.evaluation.impl;

import com.jaspersoft.jasperserver.jrsh.core.evaluation.AbstractEvaluationStrategy;
import com.jaspersoft.jasperserver.jrsh.core.operation.Operation;
import com.jaspersoft.jasperserver.jrsh.core.operation.OperationResult;
import com.jaspersoft.jasperserver.jrsh.core.operation.OperationResult.ResultCode;
import com.jaspersoft.jasperserver.jrsh.core.script.impl.ToolOperationScript;
import lombok.NonNull;

import java.util.Collection;

public class ToolEvaluationStrategy extends AbstractEvaluationStrategy<ToolOperationScript> {

    @Override @NonNull public OperationResult eval(@NonNull ToolOperationScript script) {
        Collection<String> operations = script.getSource();
        Operation operationInstance = null;
        OperationResult last = null;

        try {
            for (String operation : operations) {
                operationInstance = parser.parse(operation);
                OperationResult current = operationInstance.eval();
                last = (last != null)
                        ? current.setPrevious(last)
                        : current;
            }
        } catch (Exception error) {
            last = (last != null)
                    ? new OperationResult(error.getMessage(), ResultCode.FAILED, operationInstance, last)
                    : new OperationResult(error.getMessage(), ResultCode.FAILED, operationInstance, null);
        }
        return last;
    }

}