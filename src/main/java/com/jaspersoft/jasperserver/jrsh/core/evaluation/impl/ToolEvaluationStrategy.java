package com.jaspersoft.jasperserver.jrsh.core.evaluation.impl;

import com.jaspersoft.jasperserver.jaxrs.client.core.Session;
import com.jaspersoft.jasperserver.jrsh.core.common.SessionFactory;
import com.jaspersoft.jasperserver.jrsh.core.evaluation.AbstractEvaluationStrategy;
import com.jaspersoft.jasperserver.jrsh.core.operation.Operation;
import com.jaspersoft.jasperserver.jrsh.core.operation.OperationResult;
import com.jaspersoft.jasperserver.jrsh.core.script.Script;

import java.util.Collection;

import static com.jaspersoft.jasperserver.jrsh.core.operation.OperationResult.ResultCode.FAILED;

public class ToolEvaluationStrategy extends AbstractEvaluationStrategy {

    @Override
    public OperationResult eval(Script script) {
        Collection<String> operations = script.getSource();
        Operation operationInstance = null;
        OperationResult last = null;

        try {
            for (String operation : operations) {
                Session session = SessionFactory.getSharedSession();
                operationInstance = parser.parse(operation);
                OperationResult current = operationInstance.eval(session);

                // fixme
                // use ConsoleReader to print result
                // and save a history to .jrshhistory file

                System.out.println(current.getResultMessage());
                last = (last != null) ? current.setPrevious(last) : current;
            }
        } catch (Exception error) {
            last = (last != null)
                    ? new OperationResult(error.getMessage(), FAILED, operationInstance, last)
                    : new OperationResult(error.getMessage(), FAILED, operationInstance, null);
        }
        return last;
    }

}