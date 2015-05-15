package ua.krasnyanskiy.jrsh.operation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;
import ua.krasnyanskiy.jrsh.operation.parameter.OperationParameters;

@Data
@AllArgsConstructor
public class OperationResult {

    private String message;
    private ResultCode code;
    private Operation<? extends OperationParameters> context;

    public enum ResultCode {
        SUCCESS(0),
        FAILED(1);

        @Setter
        private int value;

        ResultCode(int value) {
            this.value = value;
        }
    }
}
