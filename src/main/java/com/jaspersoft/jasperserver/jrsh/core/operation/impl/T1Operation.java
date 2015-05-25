package com.jaspersoft.jasperserver.jrsh.core.operation.impl;

import com.jaspersoft.jasperserver.jaxrs.client.core.Session;
import com.jaspersoft.jasperserver.jrsh.core.operation.Operation;
import com.jaspersoft.jasperserver.jrsh.core.operation.OperationResult;
import com.jaspersoft.jasperserver.jrsh.core.operation.annotation.Master;

@Master(name = "t1", tail = true)
public class T1Operation implements Operation {

    @Override
    public OperationResult eval(Session session) {
        return new OperationResult("T1", OperationResult.ResultCode.SUCCESS, this, null);
    }
}