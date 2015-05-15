//package ua.krasnyanskiy.jrsh.operation.parameter;
//
//import lombok.Getter;
//import ua.krasnyanskiy.jrsh.operation.grammar.token.StringToken;
//import ua.krasnyanskiy.jrsh.operation.impl.HelpOperation;
//import ua.krasnyanskiy.jrsh.operation.parameter.annotation.Master;
//import ua.krasnyanskiy.jrsh.operation.parameter.annotation.Parameter;
//import ua.krasnyanskiy.jrsh.operation.parameter.annotation.Value;
//
//@Master(name = "help", terminal = true)
//public class HelpOperationParameters implements OperationParameters<HelpOperation> {
//
//    @Getter
//    @Parameter(mandatory = false, dependsOn = "help", values = {
//            @Value(tokenName = "export", terminal = true, tokenClass = StringToken.class, tokenValue = "export"),
//            @Value(tokenName = "login", terminal = true, tokenClass = StringToken.class, tokenValue = "login")})
//    private String context;
//}
