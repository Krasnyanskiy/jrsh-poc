package ua.krasnyanskiy.jrsh.operation.parameter.annotation;

import ua.krasnyanskiy.jrsh.operation.grammar.token.ValueToken;
import ua.krasnyanskiy.jrsh.operation.grammar.token.Token;

public @interface Dependency {
    String value() default "";

    Class<? extends Token> token() default ValueToken.class;

}
