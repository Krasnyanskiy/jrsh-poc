package ua.krasnyanskiy.jrsh.operation.parameter.converter;

public interface ParameterConverter<T> {

    T convert(String param);

}
