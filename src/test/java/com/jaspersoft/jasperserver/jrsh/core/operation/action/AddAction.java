package com.jaspersoft.jasperserver.jrsh.core.operation.action;

import com.jaspersoft.jasperserver.jaxrs.client.core.Session;
import com.jaspersoft.jasperserver.jrsh.core.operation.Operation;
import com.jaspersoft.jasperserver.jrsh.core.operation.OperationResult;
import com.jaspersoft.jasperserver.jrsh.core.operation.annotation.Master;

@Master(name = "add")
public class AddAction implements Operation {
    @Override
    public OperationResult eval(Session session) {
        return new OperationResult("Add", OperationResult.ResultCode.SUCCESS, this, null);
    }
}
