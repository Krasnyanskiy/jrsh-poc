package com.jaspersoft.jasperserver.jrsh.core.operation.impl;

import com.jaspersoft.jasperserver.jrsh.core.operation.Operation;
import com.jaspersoft.jasperserver.jrsh.core.operation.OperationResult;
import com.jaspersoft.jasperserver.jrsh.core.operation.OperationResult.ResultCode;
import com.jaspersoft.jasperserver.jrsh.core.operation.annotation.Master;
import com.jaspersoft.jasperserver.jrsh.core.operation.annotation.Parameter;
import com.jaspersoft.jasperserver.jrsh.core.operation.annotation.Value;
import com.jaspersoft.jasperserver.jrsh.core.operation.grammar.token.impl.StringToken;

@Master(name = "help", tail = true, description = "This is a help operation.")
public class HelpOperation implements Operation {

    @Parameter(dependsOn = "help", values = {
            @Value(tokenAlias = "E", tokenClass = StringToken.class, tokenValue = "export"),
            @Value(tokenAlias = "L", tokenClass = StringToken.class, tokenValue = "login")
    })
    private String context;

    @Override
    public OperationResult eval() {
        return new OperationResult("It's done!", ResultCode.SUCCESS, this, null);
    }
}
