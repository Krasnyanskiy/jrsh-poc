package com.jaspersoft.jasperserver.jrsh.core.script;

import com.jaspersoft.jasperserver.jrsh.core.script.impl.FileScript;
import com.jaspersoft.jasperserver.jrsh.core.script.impl.ShellOperationScript;
import com.jaspersoft.jasperserver.jrsh.core.script.impl.ToolOperationScript;

import static com.jaspersoft.jasperserver.jrsh.core.operation.grammar.token.TokenPreconditions.isConnectionString;
import static com.jaspersoft.jasperserver.jrsh.core.operation.grammar.token.TokenPreconditions.isScriptFileName;

public class ScriptBuilder {

    private String[] source;

    public ScriptBuilder(String[] source) {
        this.source = source;
    }

    public Script build() {
        Script script;
        if (source.length == 1 && isConnectionString(source[0])) {
            script = new ShellOperationScript(source[0]);
        }
        else if (source.length == 2
                && "--script".equals(source[0])
                && isScriptFileName(source[1])) {
            script = new FileScript(source[1]);
        }
        else {
            script = new ToolOperationScript(source);
        }
        return script;
    }
}