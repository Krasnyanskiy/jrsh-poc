package ua.krasnyanskiy.jrsh.operation.parameter.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Parameter(mandatory = true)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface Master {

}
