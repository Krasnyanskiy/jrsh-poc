package ua.krasnyanskiy.jrsh.operation.parameter.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(FIELD)
@Retention(RUNTIME)
public @interface Parameter {

    Value[] values() default {};        // the value

    String[] dependsOn() default {};    // dependencies

    boolean mandatory() default false;  // is mandatory for all rules of grammar: Rule(1*, 2, 3), Rule(1*, 3)

    String mandatoryGroup() default ""; // mandatoryGroup of parameter
}

