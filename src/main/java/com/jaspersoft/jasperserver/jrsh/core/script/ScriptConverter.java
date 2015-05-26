package com.jaspersoft.jasperserver.jrsh.core.script;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static com.jaspersoft.jasperserver.jrsh.core.operation.grammar.token.TokenPreconditions.isConnectionString;
import static com.jaspersoft.jasperserver.jrsh.core.operation.grammar.token.TokenPreconditions.isScriptFileName;
import static java.util.Arrays.asList;
import static java.util.Arrays.copyOfRange;
import static java.util.Collections.singletonList;
import static org.apache.commons.io.FileUtils.readLines;
import static org.apache.commons.lang3.StringUtils.join;

/**
 * Used to convert the initial user input (source)
 * into the {@link Script} that contains operations,
 * one operation per line.
 *
 * @author Alexander Krasnyanskiy
 * @version 1.0
 */
public class ScriptConverter { // ScriptFormatter ?

    /**
     * Format source into the script.
     *
     * @param source app parameters
     * @return script
     */
    public static Script convertToScript(String[] source) { // format
        Script script;
        switch (source.length) {
            case 0:
                script = new Script(singletonList("help"));
                break;

            case 1:
                String line = source[0];
                if (isConnectionString(line)) {
                    script = new Script(singletonList("login " + line));
                } else {
                    script = new Script(singletonList(line));
                }
                break;

            default:
                if ("--script".equals(source[0]) && isScriptFileName(source[1])) {
                    try {
                        List<String> lines = readLines(new File(source[1]));
                        script = new Script(lines);
                    } catch (IOException ignored) {
                        throw new RuntimeException("Could not open a script file: " + source[1]);
                    }
                } else if (isConnectionString(source[0])) {
                    String loginLine = "login " + source[0];
                    String nextLine = join(copyOfRange(source, 1, source.length), " ");
                    script = new Script(asList(loginLine, nextLine));
                } else {
                    line = join(source, " ");
                    script = new Script(singletonList(line));
                }
        }
        return script;
    }
}