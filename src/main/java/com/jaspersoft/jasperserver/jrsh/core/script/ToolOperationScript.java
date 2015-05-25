//package com.jaspersoft.jasperserver.jrsh.core.script.impl;
//
//import com.jaspersoft.jasperserver.jrsh.core.script.Script;
//import lombok.NonNull;
//
//import java.util.ArrayList;
//import java.util.Collection;
//
//import static com.jaspersoft.jasperserver.jrsh.core.operation.grammar.token.TokenPreconditions.isConnectionString;
//import static java.util.Arrays.copyOfRange;
//import static org.apache.commons.lang3.StringUtils.join;
//
//public class ToolOperationScript implements Script<Collection<String>> {
//
//    private Collection<String> source;
//
//    public ToolOperationScript(@NonNull String[] input) {
//        source = new ArrayList<>();
//        if (input.length == 0) {
//            source.add("help");
//        }
//        else if (isConnectionString(input[0])) {
//            source.add("login " + input[0]);
//            String line = join(copyOfRange(input, 1, input.length), " ");
//            source.add(line);
//        }
//        else {
//            source.add(join(input, " "));
//        }
//    }
//
//    @Override
//    public Collection<String> getSource() {
//        return source;
//    }
//}
