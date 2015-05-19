package com.jaspersoft.jasperserver.jrsh.core.operation.impl;

import com.jaspersoft.jasperserver.jrsh.core.operation.Operation;
import com.jaspersoft.jasperserver.jrsh.core.operation.OperationResult;
import com.jaspersoft.jasperserver.jrsh.core.operation.OperationResult.ResultCode;

public class HelpOperation implements Operation{

    @Override public OperationResult eval() {
        return new OperationResult("It's done!", ResultCode.SUCCESS, this, null);
    }
}
