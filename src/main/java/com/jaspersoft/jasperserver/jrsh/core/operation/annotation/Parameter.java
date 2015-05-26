package com.jaspersoft.jasperserver.jrsh.core.operation.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(FIELD)
@Retention(RUNTIME)
public @interface Parameter {

    String name() default ""; // by default it takes a field name for parameter name

    boolean mandatory() default false;

    String[] dependsOn() default {};

    Value[] values() default {};

    String[] ruleGroups() default {"GENERAL_GROUP"};

}
