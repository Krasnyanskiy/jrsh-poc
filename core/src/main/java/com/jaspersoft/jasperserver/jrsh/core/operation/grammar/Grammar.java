package com.jaspersoft.jasperserver.jrsh.core.operation.grammar;

import java.util.Collection;
import java.util.List;

/**
 * @author Alex Krasnyanskiy
 */
public interface Grammar {

    List<Rule> getRules();

    void addRule(Rule rule);

    void addRules(Collection<Rule> rules);

}
