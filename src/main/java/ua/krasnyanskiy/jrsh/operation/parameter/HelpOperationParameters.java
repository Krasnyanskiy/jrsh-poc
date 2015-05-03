package ua.krasnyanskiy.jrsh.operation.parameter;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ua.krasnyanskiy.jrsh.operation.parameter.annotation.Parameter;

@Data
@EqualsAndHashCode(callSuper = false)
public class HelpOperationParameters extends OperationParameters {

    @Parameter(value = "help", mandatory = true)
    private String operationName;

    @Parameter(dependsOn = "help", value = {"export", "import", "help", "login"})
    private String context;

    // fixme
    public String getContext() {
        return context;
    }
}
