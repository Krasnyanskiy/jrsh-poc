package com.jaspersoft.jasperserver.jrsh.core.operation.grammar.token;

import jline.console.completer.Completer;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public abstract class AbstractToken implements Token {

    protected String name;
    protected String value;
    protected boolean mandatory;
    protected boolean tailOfRule;

    public AbstractToken(String name, String value, boolean mandatory, boolean tailOfRule) {
        this.name = name;
        this.value = value;
        this.mandatory = mandatory;
        this.tailOfRule = tailOfRule;
    }

    @Override
    public abstract Completer getCompleter();

    @Override
    public abstract boolean match(String input);

}
