package com.jaspersoft.jasperserver.jrsh.core.operation;

import com.jaspersoft.jasperserver.jrsh.core.operation.annotation.Parameter;
import com.jaspersoft.jasperserver.jrsh.core.operation.annotation.Value;
import com.jaspersoft.jasperserver.jrsh.core.operation.grammar.token.Token;
import com.jaspersoft.jasperserver.jrsh.core.operation.parser.exception.CannotFindGetterException;
import com.jaspersoft.jasperserver.jrsh.core.operation.parser.exception.OperationParseException;
import lombok.extern.log4j.Log4j;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

@Log4j
public class OperationReflector {

    public static void set(Operation operation, List<Token> ruleTokens, List<String> inputTokens) {
        Class<? extends Operation> clazz = operation.getClass();
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {

            // Get @parameter
            Parameter param = field.getAnnotation(Parameter.class);
            if (param != null) {

                // Get @values
                Value[] values = param.values();

                for (Value value : values) {
                    String alias = value.tokenAlias();
                    int index = getIndex(ruleTokens, alias);

                    if (index >= 0) {
                        field.setAccessible(true);
                        Method setter = findSetter(clazz.getMethods(), field.getName());

                        if (setter == null) {
                            throw new CannotFindGetterException(field.getName());
                        }

                        try {
                            setter.invoke(operation, inputTokens.get(index));
                        } catch (IllegalAccessException | InvocationTargetException err) {
                            // Reflection wraps our custom exceptions, but we can get them
                            // through the cause.
                            Throwable cause = err.getCause();
                            if (OperationParseException.class.isAssignableFrom(cause.getClass())){
                                throw (RuntimeException) cause;
                            }
                        }

                        field.setAccessible(false);
                    }
                }
            }
        }
        //log.info(operation);
    }

    protected static int getIndex(List<Token> tokens, String tokenName) {
        for (int idx = 0; idx < tokens.size(); idx++) {
            Token token = tokens.get(idx);
            if (tokenName.equals(token.getName())) {
                return idx;
            }
        }
        return -1;
    }

    protected static Method findSetter(Method[] methods, String name) {
        for (Method method : methods) {
            String methodName = method.getName();
            if (methodName.startsWith("set") && methodName.toLowerCase().endsWith(name.toLowerCase())) {
                return method;
            }
        }
        return null;
    }
}
