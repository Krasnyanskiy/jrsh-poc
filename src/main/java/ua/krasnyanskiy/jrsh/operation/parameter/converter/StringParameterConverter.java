package ua.krasnyanskiy.jrsh.operation.parameter.converter;

public class StringParameterConverter implements ParameterConverter<String> {
    @Override
    public String convert(String param) {
        return param;
    }
}
