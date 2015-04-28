package ua.krasnyanskiy.jrsh.operation;

import lombok.NonNull;

/**
 * @author Alexander Krasnyanskiy
 * @since 1.0
 */
public interface ParameterParser {

    @NonNull Parameters getParameters(String... args);

}
