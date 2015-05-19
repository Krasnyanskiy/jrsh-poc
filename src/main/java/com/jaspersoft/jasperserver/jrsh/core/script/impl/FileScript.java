package com.jaspersoft.jasperserver.jrsh.core.script.impl;

import com.jaspersoft.jasperserver.jrsh.core.script.Script;

import java.io.File;

public class FileScript implements Script<File> {

    private String source;

    public FileScript(String input) {
        this.source = input;
    }

    @Override
    public File getSource() {
        return new File(source);
    }
}
