package ua.krasnyanskiy.jrsh.operation.parameter;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ua.krasnyanskiy.jrsh.operation.parameter.annotation.Parameter;

@Data
@EqualsAndHashCode(callSuper = false)
public class HelpOperationParameters extends OperationParameters {

    @Parameter(value = "help", mandatory = true)
    private String operationName = "help";

    @Parameter(dependsOn = "help", value = {"export", "login"})
    private String context;

    // fixme (lombok doesn't work)
    public String getContext() {
        return context;
    }
}
