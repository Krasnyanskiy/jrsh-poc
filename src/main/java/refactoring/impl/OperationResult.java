package refactoring.impl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import refactoring.Operation;

/**
 * Represents the result of operation evaluation.
 */
@Data
@AllArgsConstructor
public class OperationResult {

    private String massage;
    private ResultCode code;
    private Operation context;

    enum ResultCode {
        SUCCESS(0),
        FAILED(1);

        @Getter
        private int value;

        ResultCode(int value) {
            this.value = value;
        }
    }
}
