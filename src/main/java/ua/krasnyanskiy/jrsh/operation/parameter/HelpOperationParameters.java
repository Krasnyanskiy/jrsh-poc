package ua.krasnyanskiy.jrsh.operation.parameter;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ua.krasnyanskiy.jrsh.operation.grammar.token.StringToken;
import ua.krasnyanskiy.jrsh.operation.parameter.annotation.Master;
import ua.krasnyanskiy.jrsh.operation.parameter.annotation.Parameter;
import ua.krasnyanskiy.jrsh.operation.parameter.annotation.Value;

@Data
@Master(name = "help", terminal = true)
@EqualsAndHashCode(callSuper = false)
public class HelpOperationParameters extends AbstractOperationParameters {
    @Parameter(mandatory = false, dependsOn = "help", values = {
            @Value(tokenName = "export", terminal = true, tokenClass = StringToken.class, tokenValue = "export"),
            @Value(tokenName = "login", terminal = true, tokenClass = StringToken.class, tokenValue = "login")
    })
    private String context;
}
