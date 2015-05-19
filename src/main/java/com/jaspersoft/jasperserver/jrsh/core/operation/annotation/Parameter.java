package com.jaspersoft.jasperserver.jrsh.core.operation.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Operation metadata.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Parameter {

    /**
     * Is used to combine values (@Value)
     *
     * @return name
     */
    String name() default "field"; // by default it takes a field name for parameter name

    /**
     * Defines if parameter is mandatory for group of
     * tokens.
     *
     * @return true if mandatory
     */
    boolean mandatory() default false;

    /**
     * Defines an array of {@link Value} names representing
     * dependencies.
     *
     * @return name values
     */
    String[] dependsOn() default {};

    /**
     * Parameter values.
     *
     * @return values
     */
    Value[] values() default {};


    /**
     * Defines a group of tokens for the family rules.
     * <p>
     * Example:
     * Group1: {L*, S*, U*, P*, O}
     * Group2: {L*, LT}
     *
     * @return group name
     */
    String[] ruleGroups() default {"GENERAL_GROUP"};

}
