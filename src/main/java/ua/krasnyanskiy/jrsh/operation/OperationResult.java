package ua.krasnyanskiy.jrsh.operation;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OperationResult {
    private String message;
    private ResultCode code;



    public enum ResultCode {

        FAILED(1),
        SUCCESS(0);

        private int code;

        ResultCode(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }
    }
}
