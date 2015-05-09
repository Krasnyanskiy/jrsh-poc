package ua.krasnyanskiy.jrsh.operation.parameter.annotation;

import ua.krasnyanskiy.jrsh.operation.grammar.token.Token;
import ua.krasnyanskiy.jrsh.operation.grammar.token.ValueToken;
import ua.krasnyanskiy.jrsh.operation.parameter.converter.ParameterConverter;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(FIELD)
@Retention(RUNTIME)
public @interface Parameter {

    String tokenName() default "";

    String[] tokenValue() default {};

    String[] dependsOn() default {};

    boolean mandatory() default false;

    Class<? extends Token> token() default ValueToken.class;

    boolean terminal() default false;

    Class<? extends ParameterConverter> converter() default ParameterConverter.DefaultParameterConverter.class;

    //boolean interconnected() default false;
}
