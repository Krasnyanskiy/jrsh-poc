package ua.krasnyanskiy.jrsh;

import org.junit.Test;
import ua.krasnyanskiy.jrsh.operation.parameter.ExportOperationParameters;
import ua.krasnyanskiy.jrsh.operation.parameter.OperationParameters;
import ua.krasnyanskiy.jrsh.operation.parameter.annotation.Parameter;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

public class Reflection {

    @Test
    public void shouldReturnProperSetOfValues() {
        Set<String> allTokens = new Reflection().getValues(new ExportOperationParameters());
        assertThat(allTokens).contains("export", "to", "repository", "role", "with-include-access-events");
    }

    @Test
    public void shouldReturnParents() {
        Set<String> parents = new Reflection().getParents(new ExportOperationParameters());
        assertThat(parents).contains("export", "to", "repository", "role");
    }

    @Test
    public void shouldSetFieldOfParameter() throws IllegalAccessException {
        ExportOperationParameters parameter = new ExportOperationParameters();
        setField(parameter, "repository", "all");
        assertThat(parameter.getContext()).isEqualTo("all");
    }

    /**
     * @param param     given parameter
     * @param valueName annotation parameter `value`
     * @param val       value of the field
     * @throws IllegalAccessException
     */
    public void setField(OperationParameters param, String valueName, String val) throws IllegalAccessException {
        Class<? extends OperationParameters> paramClass = param.getClass();
        Field[] fields = paramClass.getDeclaredFields();
        for (Field f : fields) {
            Parameter meta = f.getAnnotation(Parameter.class);
            if (meta != null) { // exclude $this
                String[] tokens = meta.value();
                if (asList(tokens).contains(valueName)) {
                    f.setAccessible(true);
                    f.set(param, val);
                    f.setAccessible(false);
                }
            }
        }
    }

    public Set<String> getValues(ExportOperationParameters export) {
        Set<String> tokensSet = new HashSet<>();
        Class<? extends ExportOperationParameters> exportParamsClass = export.getClass();
        Field[] fields = exportParamsClass.getDeclaredFields();

        for (Field field : fields) {
            Parameter meta = field.getAnnotation(Parameter.class);
            if (meta != null) { // exclude $this
                String[] tokens = meta.value();
                tokensSet.addAll(asList(tokens));
            }
        }
        return tokensSet;
    }

    public Set<String> getParents(ExportOperationParameters export) {
        Set<String> tokensSet = new HashSet<>();
        Class<? extends ExportOperationParameters> exportParamsClass = export.getClass();
        Field[] fields = exportParamsClass.getDeclaredFields();

        for (Field field : fields) {
            Parameter meta = field.getAnnotation(Parameter.class);
            if (meta != null) {
                String[] masters = meta.dependsOn(); // parents
                tokensSet.addAll(asList(masters));
            }
        }
        return tokensSet;
    }

}
