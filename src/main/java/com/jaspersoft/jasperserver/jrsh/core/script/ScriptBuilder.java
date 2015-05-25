package com.jaspersoft.jasperserver.jrsh.core.script;

import com.jaspersoft.jasperserver.jrsh.core.evaluation.impl.MultilineScriptEvaluationStrategy;
import com.jaspersoft.jasperserver.jrsh.core.evaluation.impl.ShellEvaluationStrategy;
import com.jaspersoft.jasperserver.jrsh.core.evaluation.impl.ToolEvaluationStrategy;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.jaspersoft.jasperserver.jrsh.core.operation.grammar.token.TokenPreconditions.isConnectionString;
import static com.jaspersoft.jasperserver.jrsh.core.operation.grammar.token.TokenPreconditions.isScriptFileName;

public class ScriptBuilder {

    private String[] source;

    public ScriptBuilder(String[] source) {
        this.source = source;
    }

    public Script build() {
        Script script;

        if (source.length == 0) {
            script = new SimpleScript(ToolEvaluationStrategy.class, Collections.singletonList("help"));
        }
        else if (source.length == 1 && isConnectionString(source[0])) {
            script = new SimpleScript(ShellEvaluationStrategy.class, Collections.singletonList("login " + source[0]));
        }
        else if (source.length == 2 && "--script".equals(source[0]) && isScriptFileName(source[1])) {
            try {
                List<String> lines = FileUtils.readLines(new File(source[1]));
                script = new SimpleScript(MultilineScriptEvaluationStrategy.class, lines);
            } catch (IOException ignored) {
                throw new RuntimeException("Could not open a script file: " + source[1]);
            }
        }
        else {
            script = new SimpleScript(ToolEvaluationStrategy.class, Arrays.asList(source));
        }
        return script;
    }
}