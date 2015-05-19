package com.jaspersoft.jasperserver.jrsh.core.operation;

import com.jaspersoft.jasperserver.jrsh.core.operation.grammar.Grammar;
import com.jaspersoft.jasperserver.jrsh.core.operation.grammar.Rule;
import lombok.NonNull;

import java.util.Collection;
import java.util.List;

public class OperationGrammarFactory {

    public static @NonNull Grammar getOperationGrammar(Operation operation){

        // TODO read metadata and return it to requestor
        return new Grammar() {
            @Override
            public List<Rule> getRules() {
                return null;
            }

            @Override
            public void addRule(Rule rule) {

            }

            @Override
            public void addRules(Collection<Rule> rules) {

            }
        };
    }

}
