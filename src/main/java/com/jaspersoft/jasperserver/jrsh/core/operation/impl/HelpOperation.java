package com.jaspersoft.jasperserver.jrsh.core.operation.impl;

import com.jaspersoft.jasperserver.jaxrs.client.core.Session;
import com.jaspersoft.jasperserver.jrsh.core.operation.Operation;
import com.jaspersoft.jasperserver.jrsh.core.operation.OperationResult;
import com.jaspersoft.jasperserver.jrsh.core.operation.OperationResult.ResultCode;
import com.jaspersoft.jasperserver.jrsh.core.operation.annotation.Master;
import com.jaspersoft.jasperserver.jrsh.core.operation.annotation.Parameter;
import com.jaspersoft.jasperserver.jrsh.core.operation.annotation.Value;
import com.jaspersoft.jasperserver.jrsh.core.operation.grammar.token.impl.StringToken;
import com.jaspersoft.jasperserver.jrsh.core.operation.OperationFactory;
import lombok.Data;

@Data
@Master(name = "help", tail = true, description = "This is a help operation.")
public class HelpOperation implements Operation {

    @Parameter(dependsOn = "help", values = {
            @Value(tokenAlias = "E", tokenClass = StringToken.class, tail = true, tokenValue = "export"),
            @Value(tokenAlias = "L", tokenClass = StringToken.class, tail = true, tokenValue = "login")
    })
    private String context;

    @Override
    public OperationResult eval(Session unimportant) {
        StringBuilder builder = new StringBuilder();
        if (context != null) {
            Operation operation = OperationFactory.getOperationByName(context);
            builder.append(getDescription(operation))/*.append("\n")*/;
        } else {
            builder.append("\nUsage (Tool):   \u001B[37mjrsh\u001B[0m username%password@url <operation> <parameters>\n");
            builder.append("Usage (Shell):  \u001B[37mjrsh\u001B[0m username%password@url\n");
            builder.append("Usage (Script): \u001B[37mjrsh\u001B[0m script.jrs\n");
            builder.append("\nAvailable operations: \n");

            for (Operation operation : OperationFactory.getAvailableOperations()) {
                builder.append(getDescription(operation)).append("\n");
            }
        }

        return new OperationResult(builder.toString(), ResultCode.SUCCESS, this, null);
    }

    protected String getDescription(Operation operation) {
        Master master = operation.getClass().getAnnotation(Master.class);
        return master.description();
    }
}
