package ua.krasnyanskiy.jrsh.operation.parameter.annotation;

import ua.krasnyanskiy.jrsh.operation.grammar.token.Token;
import ua.krasnyanskiy.jrsh.operation.grammar.token.ValueToken;
import ua.krasnyanskiy.jrsh.operation.parameter.converter.ParameterConverter;
import ua.krasnyanskiy.jrsh.operation.parameter.converter.StringParameterConverter;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(FIELD)
@Retention(RUNTIME)
public @interface Parameter {

    boolean mandatory() default false;

    String[] dependsOn() default {};

    Class<? extends Token> token() default ValueToken.class;

    Class<? extends ParameterConverter> converter() default StringParameterConverter.class;

    String[] value() default {};

    boolean endPoint() default false;

    boolean interconnected() default false;

}
