package ua.krasnyanskiy.jrsh.common;

import lombok.SneakyThrows;
import ua.krasnyanskiy.jrsh.exception.WrongParametersFormatException;
import ua.krasnyanskiy.jrsh.operation.grammar.token.Token;
import ua.krasnyanskiy.jrsh.operation.parameter.AbstractOperationParameters;
import ua.krasnyanskiy.jrsh.operation.parameter.annotation.Parameter;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

public class Reflection {

    @SneakyThrows
    public static void setParametersValues(final AbstractOperationParameters parameters,
                                           final List<Token> tokens,
                                           final String[] values) {
        Class<? extends AbstractOperationParameters> clazz = parameters.getClass();
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            Parameter meta = field.getAnnotation(Parameter.class);
            if (meta != null) {
                for (int i = 1/*exclude operation name*/; i < tokens.size(); i++) {
                    String tokenName = tokens.get(i).getName();
                    String tokenValue = values[i];

                    if (tokenName.equals(meta.tokenName())) {
                        field.setAccessible(true);
                        Method setter = Reflection.findSetter(clazz.getMethods(), field.getName());

                        if (setter == null) {
                            throw new WrongParametersFormatException(field.getName());
                        }
                        setter.invoke(parameters, tokenValue);
                        field.setAccessible(false);
                    }

                }
            }
        }

    }

    public static Method findSetter(Method[] methods, String name) {
        for (Method method : methods) {
            String methodName = method.getName();
            if (methodName.startsWith("set") && methodName.toLowerCase()
                    .endsWith(name.toLowerCase())) {
                return method;
            }
        }
        return null;
    }
}
