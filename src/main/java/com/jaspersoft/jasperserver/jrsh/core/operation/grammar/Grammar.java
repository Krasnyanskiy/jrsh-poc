package com.jaspersoft.jasperserver.jrsh.core.operation.grammar;

import java.util.Collection;
import java.util.List;

/**
 * This class represents is a simple collection
 * of rules governing the composition of tokens.
 *
 * @author Alexander Krasnyanskiy
 * @version 1.0
 */
public interface Grammar {

    /**
     * Returns the rules of a grammar.
     *
     * @return rules
     */
    List<Rule> getRules();

    /**
     * Add a new rule to the grammar.
     *
     * @param rule a new rule
     */
    void addRule(Rule rule);

    /**
     * Add rules to the grammar.
     *
     * @param rules new rules
     */
    void addRules(Collection<Rule> rules);
}
