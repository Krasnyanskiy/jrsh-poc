package ua.krasnyanskiy.jrsh.operation;

import lombok.AllArgsConstructor;
import lombok.Data;
import ua.krasnyanskiy.jrsh.operation.parameter.AbstractOperationParameters;

@Data
@AllArgsConstructor
public class EvaluationResult {
    private String message;
    private ResultCode code;
    private Operation<? extends AbstractOperationParameters> context;

    public enum ResultCode {
        SUCCESS(0),
        FAILED(1);
        private int code;
        ResultCode(int code) {
            this.code = code;
        }
        public int getCode() {
            return code;
        }
    }
}
