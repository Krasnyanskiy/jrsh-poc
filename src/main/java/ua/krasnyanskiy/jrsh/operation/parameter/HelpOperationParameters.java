package ua.krasnyanskiy.jrsh.operation.parameter;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ua.krasnyanskiy.jrsh.operation.parameter.annotation.Master;
import ua.krasnyanskiy.jrsh.operation.parameter.annotation.Parameter;

@Data
@Master("help")
@EqualsAndHashCode(callSuper = false)
public class HelpOperationParameters extends AbstractOperationParameters {
    @Parameter(tokenName = "context", tokenValue = {"export", "login"}, dependsOn = "help")
    private String context;
}
