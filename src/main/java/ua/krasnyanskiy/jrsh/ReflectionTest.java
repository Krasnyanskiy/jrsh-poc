package ua.krasnyanskiy.jrsh;

import ua.krasnyanskiy.jrsh.operation.parameter.ExportOperationParameters;
import ua.krasnyanskiy.jrsh.operation.parameter.annotation.Parameter;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

import static java.util.Arrays.asList;

public class ReflectionTest {
    public static void main(String[] args) {
        new ReflectionTest().printContent(new ExportOperationParameters());
    }


    public void printContent(ExportOperationParameters export) {

        Set<String> tokens_ = new HashSet<>();

        Class<? extends ExportOperationParameters> exportParamsClass = export.getClass();
        Field[] fields = exportParamsClass.getDeclaredFields();
        for (Field field : fields) {
            Parameter meta = field.getAnnotation(Parameter.class);
            if (meta != null) { // exclude $this
                String[] parent = meta.dependsOn();
                String[] tokens = meta.value();
                tokens_.addAll(asList(tokens));
                //System.out.printf("[%s][%s]\n", asList(parent), asList(tokens));
            }
        }
    }

}
