package com.jaspersoft.jasperserver.jrsh.core.operation.impl;

import com.jaspersoft.jasperserver.jaxrs.client.core.Session;
import com.jaspersoft.jasperserver.jrsh.core.operation.Operation;
import com.jaspersoft.jasperserver.jrsh.core.operation.OperationResult;
import com.jaspersoft.jasperserver.jrsh.core.operation.OperationResult.ResultCode;
import com.jaspersoft.jasperserver.jrsh.core.operation.annotation.Master;
import com.jaspersoft.jasperserver.jrsh.core.operation.annotation.Parameter;
import com.jaspersoft.jasperserver.jrsh.core.operation.annotation.Value;
import com.jaspersoft.jasperserver.jrsh.core.operation.grammar.token.impl.StringToken;
import lombok.Data;

@Data
@Master(name = "dummy", description = "Dummy operation.", tail = true)
public class DummyOperation implements Operation {

    @Parameter(dependsOn = "dummy", values =
    @Value(tokenAlias = "V", tail = true, tokenClass = StringToken.class, tokenValue = "--verbose"))
    private String verbose;

    @Override
    public OperationResult eval(Session session) {
        if (verbose == null) {
            System.out.println("Hi!");
        } else {
            System.out.println("-v Hi!");
        }
        return new OperationResult("Dummy operation!", ResultCode.SUCCESS, this, null);
    }
}
