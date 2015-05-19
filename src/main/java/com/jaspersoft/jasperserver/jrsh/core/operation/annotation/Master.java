package com.jaspersoft.jasperserver.jrsh.core.operation.annotation;

import com.jaspersoft.jasperserver.jrsh.core.operation.grammar.token.impl.StringToken;
import com.jaspersoft.jasperserver.jrsh.core.operation.grammar.token.Token;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Included in all TokenRuleGroups.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Master {

    String name() default "";

    boolean tail() default false;

    String description() default "";

    Class<? extends Token> tokenClass()
            default StringToken.class;

}

