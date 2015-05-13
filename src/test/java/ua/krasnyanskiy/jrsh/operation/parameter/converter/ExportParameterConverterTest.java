//package ua.krasnyanskiy.jrsh.operation.parameter.converter;
//
//import com.jaspersoft.jasperserver.jaxrs.client.apiadapters.importexport.exportservice.ExportParameter;
//import org.junit.Test;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//public class ExportParameterConverterTest {
//
//    private ParameterConverter<ExportParameter> converter = new ExportParameterConverter();
//
//    @Test
//    public void shouldReturnProperParameter() {
//        ExportParameter retrieved = converter.convert("all");
//        assertThat(retrieved).isEqualTo(ExportParameter.EVERYTHING);
//    }
//}