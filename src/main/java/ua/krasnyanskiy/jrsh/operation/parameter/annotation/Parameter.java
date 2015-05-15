package ua.krasnyanskiy.jrsh.operation.parameter.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(FIELD)
@Retention(RUNTIME)
public @interface Parameter {

    Value[] values() default {};        // the values

    String[] dependsOn() default {};    // dependencies

    boolean mandatory() default false;  // define if parameter is mandatory for whole chain of tokens:
                                        // 1 - Rule(1*, 2, 3),
                                        // 2 - Rule(1*, 3)
                                        // Chain {1 -> 2 -> 3}


}

