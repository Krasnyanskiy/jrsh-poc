package ua.krasnyanskiy.jrsh.operation.parameter.annotation;

import ua.krasnyanskiy.jrsh.operation.grammar.token.StringToken;
import ua.krasnyanskiy.jrsh.operation.grammar.token.Token;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(TYPE)
@Retention(RUNTIME)
public @interface Master {

    String name() default "";

    boolean terminal() default false;

    Class<? extends Token> token() default StringToken.class;
}