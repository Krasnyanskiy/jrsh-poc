package com.jaspersoft.jasperserver.jrsh.core.common;

import java.util.List;

public class Script {
    private List<String> source;

    public Script(List<String> source) {
        this.source = source;
    }

    public List<String> getSource() {
        return source;
    }
}
