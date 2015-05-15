//package ua.krasnyanskiy.jrsh;
//
//import org.junit.Before;
//import org.junit.Rule;
//import org.junit.Test;
//import org.junit.rules.ExpectedException;
//import refactoring.OperationParser;
//import refactoring.impl.Lexer;
//import refactoring.impl.OperationParserImpl;
//import refactoring.impl.ParseException;
//import refactoring.impl.ParseParametersException;
//
//public class MyTest {
//
//    public OperationParser parser;
//
//    @Rule
//    public ExpectedException throwing = ExpectedException.none();
//
//    @Before
//    public void before() {
//        parser = new OperationParserImpl(new Lexer());
//    }
//
//    @Test
//    public void should() throws ParseException {
//        throwing.expect(ParseParametersException.class);
//        parser.parse("abc");
//    }
//}
