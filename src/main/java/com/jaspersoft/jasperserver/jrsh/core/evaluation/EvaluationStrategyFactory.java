package com.jaspersoft.jasperserver.jrsh.core.evaluation;

import com.jaspersoft.jasperserver.jrsh.core.evaluation.impl.FileScriptEvaluationStrategy;
import com.jaspersoft.jasperserver.jrsh.core.evaluation.impl.ShellEvaluationStrategy;
import com.jaspersoft.jasperserver.jrsh.core.evaluation.impl.ToolEvaluationStrategy;
import com.jaspersoft.jasperserver.jrsh.core.script.Script;
import com.jaspersoft.jasperserver.jrsh.core.script.impl.FileScript;
import com.jaspersoft.jasperserver.jrsh.core.script.impl.ShellOperationScript;
import com.jaspersoft.jasperserver.jrsh.core.script.impl.ToolOperationScript;
import lombok.NonNull;

public class EvaluationStrategyFactory {

    @NonNull public static EvaluationStrategy getStrategy(Class<? extends Script> scriptType) {
        EvaluationStrategy strategy = null;

        if (scriptType == ShellOperationScript.class) {
            strategy = new ShellEvaluationStrategy();
        }

        if (scriptType == ToolOperationScript.class) {
            strategy = new ToolEvaluationStrategy();
        }

        if (scriptType == FileScript.class){
            strategy = new FileScriptEvaluationStrategy();
        }

        return strategy;
    }

}
