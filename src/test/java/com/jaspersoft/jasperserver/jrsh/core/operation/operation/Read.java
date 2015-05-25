package com.jaspersoft.jasperserver.jrsh.core.operation.operation;

import com.jaspersoft.jasperserver.jaxrs.client.core.Session;
import com.jaspersoft.jasperserver.jrsh.core.operation.Operation;
import com.jaspersoft.jasperserver.jrsh.core.operation.OperationResult;
import com.jaspersoft.jasperserver.jrsh.core.operation.annotation.Master;

@Master(name = "read")
public class Read implements Operation{
    @Override
    public OperationResult eval(Session session) {
        return new OperationResult("Read", OperationResult.ResultCode.SUCCESS, this, null);
    }
}
