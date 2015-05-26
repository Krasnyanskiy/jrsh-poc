package com.jaspersoft.jasperserver.jrsh.core.script;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Script {
    private List<String> source;

//    public List<String> getSource(){
//        return source;
//    }
}
