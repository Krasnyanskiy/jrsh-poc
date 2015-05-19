package com.jaspersoft.jasperserver.jrsh.core.operation.impl;

import com.jaspersoft.jasperserver.jrsh.core.operation.Operation;
import com.jaspersoft.jasperserver.jrsh.core.operation.OperationResult;
import com.jaspersoft.jasperserver.jrsh.core.operation.OperationResult.ResultCode;
import com.jaspersoft.jasperserver.jrsh.core.operation.annotation.Master;

@Master(name = "export", description = "La-la-la-la")
public class ExportOperation implements Operation {
    @Override
    public OperationResult eval() {
        return new OperationResult("I'm export", ResultCode.SUCCESS, this, null);
    }
}
