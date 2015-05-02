package ua.krasnyanskiy.jrsh.operation.parameter.annotation;

import ua.krasnyanskiy.jrsh.operation.grammar.token.StringToken;
import ua.krasnyanskiy.jrsh.operation.grammar.token.Token;
import ua.krasnyanskiy.jrsh.operation.parameter.converter.ParameterConverter;
import ua.krasnyanskiy.jrsh.operation.parameter.converter.StringParameterConverter;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(FIELD)
@Retention(RUNTIME)
public @interface Parameter {

    boolean ambivalent() default false;

    boolean mandatory() default false;

    String[] dependsOn() default {};

    Class<? extends Token> token() default StringToken.class;

    Class<? extends ParameterConverter> converter() default StringParameterConverter.class;

    String[] value() default {};

    boolean interconnected() default false;

    Dependency[] dependencies() default {};

}
