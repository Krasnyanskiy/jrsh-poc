//package ua.krasnyanskiy.jrsh.operation.parser;
//
//import org.junit.Test;
//import ua.krasnyanskiy.jrsh.operation.Operation;
//import ua.krasnyanskiy.jrsh.operation.impl.ExportOperation;
//import ua.krasnyanskiy.jrsh.operation.parameter.OperationParameters;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//public class LL1OperationParserTest {
//
//    private OperationParserFacade parser = new OperationParserFacade();
//
//    @Test
//    public void shouldParseOperationParameters() {
//        Operation export = new ExportOperation();
//        OperationParameters params = parser.getParameters(export, new String[]{"export", "repository", "/public/Samples"});
//        assertThat(params).isNotNull();
//    }
//}