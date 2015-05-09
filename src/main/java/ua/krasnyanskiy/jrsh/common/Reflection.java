package ua.krasnyanskiy.jrsh.common;

import lombok.SneakyThrows;
import ua.krasnyanskiy.jrsh.operation.grammar.token.Token;
import ua.krasnyanskiy.jrsh.operation.parameter.AbstractOperationParameters;
import ua.krasnyanskiy.jrsh.operation.parameter.annotation.Parameter;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

public class Reflection {

//    private static final ExportParameterConverter converter = new ExportParameterConverter();

//    @SneakyThrows
//    public static void setParameterValue(AbstractOperationParameters param, String key, String tokenValue) {
//        Class<? extends AbstractOperationParameters> clazz = param.getClass();
//        Field[] fields = clazz.getDeclaredFields();
//
//        for (Field field : fields) {
//            Parameter meta = field.getAnnotation(Parameter.class);
//            if (meta != null) {
//                String[] tokens = meta.tokenValue();
//                if (asList(tokens).contains(key) || key.equals(field.getName())) {
//                    field.setAccessible(true);
//                    Class<?> fieldType = field.getType();
//                    if (fieldType == boolean.class) {
//                        field.set(param, true);
//                    } else if (fieldType == List.class) {
//                        if ((((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0] == ExportParameter.class)) {
//                            ExportParameter convertedVal = converter.convert(tokenValue);
//                            if (field.get(param) == null) {
//                                field.set(param, new ArrayList<ExportParameter>());
//                            }
//                            ((ExportOperationParameters) param).getExportParameters().add(convertedVal);
//                        }
//                    } else {
//                        field.set(param, tokenValue);
//                    }
//                    field.setAccessible(false);
//                }
//            }
//        }
//    }


    @SneakyThrows
    public static void setParametersValues(AbstractOperationParameters parameters, List<Token> tokens, String[] values) {

        Class<? extends AbstractOperationParameters> clazz = parameters.getClass();
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            Parameter meta = field.getAnnotation(Parameter.class);
            //Prefix prefix = field.getAnnotation(Prefix.class);

            if (meta != null) {

                for (int i = 1/*exclude operation name*/; i < tokens.size(); i++) {

                    String tokenName = tokens.get(i).getName();
                    String tokenValue = values[i];

                    if (tokenName.equals(meta.tokenName()) /*|| asList(meta.tokenValue()).contains(tokenName)*/) {
                        field.setAccessible(true);
                        Method setter = Reflection.findSetter(clazz.getMethods(), field.getName());

                        if (setter == null) {
                            throw new RuntimeException(String.format("Cannot find setter for the field (%s)", field.getName()));
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
            if (methodName.startsWith("set") && methodName.toLowerCase().endsWith(name.toLowerCase())) {
                return method;
            }
        }
        return null;
    }
}
