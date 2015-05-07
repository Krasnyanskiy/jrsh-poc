package ua.krasnyanskiy.jrsh.operation.parameter.converter;

public interface ParameterConverter<T> {

    T convert(String param);

    /** Default implementation of converter **/
    class DefaultParameterConverter implements ParameterConverter<String> {
        @Override
        public String convert(String param) {
            return param; // just return the same parameter
        }
    }
}
