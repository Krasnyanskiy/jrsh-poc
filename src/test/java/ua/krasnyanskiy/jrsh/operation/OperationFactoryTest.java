package ua.krasnyanskiy.jrsh.operation;

import org.junit.Test;
import ua.krasnyanskiy.jrsh.operation.impl.ExportOperation;
import ua.krasnyanskiy.jrsh.operation.impl.HelpOperation;
import ua.krasnyanskiy.jrsh.operation.impl.LoginOperation;
import ua.krasnyanskiy.jrsh.operation.parameter.AbstractOperationParameters;

import static org.assertj.core.api.Assertions.assertThat;

public class OperationFactoryTest {

    @Test
    public void shouldReturnProperLoginOperation() {
        Operation<? extends AbstractOperationParameters> login = OperationFactory.getOperation("login");
        assertThat(login).isNotNull().isExactlyInstanceOf(LoginOperation.class);
    }

    @Test
    public void shouldReturnProperExportOperation() {
        Operation<? extends AbstractOperationParameters> login = OperationFactory.getOperation("export");
        assertThat(login).isNotNull().isExactlyInstanceOf(ExportOperation.class);
    }

    @Test
    public void shouldReturnProperHelpOperation() {
        Operation<? extends AbstractOperationParameters> login = OperationFactory.getOperation("help");
        assertThat(login).isNotNull().isExactlyInstanceOf(HelpOperation.class);
    }

}