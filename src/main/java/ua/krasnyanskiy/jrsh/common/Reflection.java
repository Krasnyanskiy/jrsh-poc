//package ua.krasnyanskiy.jrsh.common;
//
//import lombok.SneakyThrows;
//import ua.krasnyanskiy.jrsh.exception.WrongParametersFormatException;
//import ua.krasnyanskiy.jrsh.operation.grammar.token.Token;
//import ua.krasnyanskiy.jrsh.operation.parameter.AbstractOperationParameters;
//import ua.krasnyanskiy.jrsh.operation.parameter.annotation.Parameter;
//import ua.krasnyanskiy.jrsh.operation.parameter.annotation.Value;
//
//import java.lang.reflect.Field;
//import java.lang.reflect.Method;
//import java.util.List;
//
//public class Reflection {
//
//    /**
//     * @param entity      operation parameters
//     * @param ruleTokens  tokens of rule
//     * @param tokenValues user input
//     */
//    @SneakyThrows
//    public static void setParametersValues(AbstractOperationParameters entity, List<Token> ruleTokens, String[] tokenValues) {
//        Class<? extends AbstractOperationParameters> clazz = entity.getClass();
//        Field[] fields = clazz.getDeclaredFields();
//
//        for (Field field : fields) {
//            Parameter meta = field.getAnnotation(Parameter.class);
//            if (meta != null) {
//                Value[] vals = meta.values();
//                for (Value val : vals) {
//                    String tName = val.tokenName();
//                    for (int i = 0; i < ruleTokens.size(); i++) {
//                        Token token = ruleTokens.getOperationByName(i);
//                        String __tName = token.getName();
//                        if (tName.equals(__tName)) {
//                            String __val = tokenValues[i];
//                            field.setAccessible(true);
//                            Method setter = Reflection.findSetter(clazz.getMethods(), field.getName());
//                            if (setter == null) {
//                                throw new WrongParametersFormatException(field.getName());
//                            }
//                            setter.invoke(entity, __val);
//                            field.setAccessible(false);
//                        }
//                    }
//                }
//            }
//        }
//    }
//
//    /**
//     * Searches setter method
//     *
//     * @param methods   setter
//     * @param fieldName name of field
//     * @return setter method
//     */
//    public static Method findSetter(Method[] methods, String fieldName) {
//        for (Method method : methods) {
//            String methodName = method.getName().toLowerCase();
//            if (methodName.equals("set" + fieldName.toLowerCase())) {
//                return method;
//            }
//        }
//        return null;
//    }
//}
