package ua.krasnyanskiy.jrsh.operation.parameter.annotation;

import ua.krasnyanskiy.jrsh.operation.grammar.token.Token;
import ua.krasnyanskiy.jrsh.operation.grammar.token.ValueToken;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(FIELD)
@Retention(RUNTIME)
public @interface Value {

    String tokenName() default "";                                  // token name (or use field name by default)

    String tokenValue() default "";                                 // token value

    boolean terminal() default false;                               // terminalis

    Class<? extends Token> tokenClass() default ValueToken.class;   // token class

}

