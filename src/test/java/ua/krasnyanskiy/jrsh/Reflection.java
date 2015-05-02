package ua.krasnyanskiy.jrsh;

import org.junit.Test;
import ua.krasnyanskiy.jrsh.operation.parameter.ExportOperationParameters;
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

    public Set<String> getValues(ExportOperationParameters export) {
        Set<String> tokensSet = new HashSet<>();
        Class<? extends ExportOperationParameters> exportParamsClass = export.getClass();
        Field[] fields = exportParamsClass.getDeclaredFields();

        for (Field field : fields) {
            Parameter meta = field.getAnnotation(Parameter.class);
            if (meta != null) { // exclude $this
                //
                // return all tokens
                //
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
                //
                // return all parents
                //
                String[] masters = meta.dependsOn(); // parents
                tokensSet.addAll(asList(masters));
            }
        }
        return tokensSet;
    }

}
