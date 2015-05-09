package ua.krasnyanskiy.jrsh.operation.parameter;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ua.krasnyanskiy.jrsh.operation.parameter.annotation.Parameter;

@Data
@EqualsAndHashCode(callSuper = false)
public class HelpOperationParameters extends AbstractOperationParameters {

    @Parameter(tokenName = "help", mandatory = true)
    private String operationName = "help";

    @Parameter(tokenName = "context", tokenValue = {"export", "login"}, dependsOn = "help")
    private String context;

    // fixme (lombok doesn't work)
    public String getContext() {
        return context;
    }
}
