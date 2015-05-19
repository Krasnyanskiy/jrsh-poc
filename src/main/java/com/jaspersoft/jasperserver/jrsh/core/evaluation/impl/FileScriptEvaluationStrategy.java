package com.jaspersoft.jasperserver.jrsh.core.evaluation.impl;

import com.jaspersoft.jasperserver.jrsh.core.evaluation.AbstractEvaluationStrategy;
import com.jaspersoft.jasperserver.jrsh.core.operation.OperationResult;
import com.jaspersoft.jasperserver.jrsh.core.script.impl.FileScript;

import java.io.File;

public class FileScriptEvaluationStrategy extends AbstractEvaluationStrategy<FileScript> {
    @Override
    public OperationResult eval(FileScript script) {
        File source = script.getSource();
        // read and evaluate
        return null;
    }
}
