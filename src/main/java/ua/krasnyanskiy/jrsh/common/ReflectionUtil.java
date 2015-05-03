package ua.krasnyanskiy.jrsh.common;

import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.importexport.exportservice.ExportParameter;
import lombok.SneakyThrows;
import ua.krasnyanskiy.jrsh.operation.parameter.ExportOperationParameters;
import ua.krasnyanskiy.jrsh.operation.parameter.OperationParameters;
import ua.krasnyanskiy.jrsh.operation.parameter.annotation.Parameter;
import ua.krasnyanskiy.jrsh.operation.parameter.converter.ExportParameterConverter;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import static java.util.Arrays.asList;

public class ReflectionUtil {

    private static final ExportParameterConverter converter = new ExportParameterConverter();

    /**
     * Adds value to the Parameter
     *
     * @param param parameter
     * @param key   value key (@Parameter(value = "export") => key is `export`)
     * @param value field value
     */
    @SneakyThrows
    public static void setParameterValue(OperationParameters param, String key, String value) {
        Class<? extends OperationParameters> clazz = param.getClass();
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            Parameter meta = field.getAnnotation(Parameter.class);

            if (meta != null) { // exclude $this
                String[] tokens = meta.value();

                if (asList(tokens).contains(key)) {
                    field.setAccessible(true);
                    Class<?> fieldType = field.getType();

                    if (fieldType == boolean.class) { // for [private Boolean to;] in ExportOperationParameters
                        field.set(param, true);
                    } else if (fieldType == List.class) { // yet another dirty trick
                        // get type variable of generic
                        if ((((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0] == ExportParameter.class)) {
                            ExportParameter convertedVal = converter.convert(value);
                            ((ExportOperationParameters) param).getExportParameters().add(convertedVal); // very dirty!
                        }
                    } else {
                        field.set(param, value);
                    }
                    field.setAccessible(false);
                }
            }
        }
    }

}
