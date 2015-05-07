package ua.krasnyanskiy.jrsh.evaluation;

import jline.console.ConsoleReader;
import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.internal.util.reflection.Whitebox;
import org.powermock.api.support.membermodification.MemberMatcher;
import org.powermock.api.support.membermodification.MemberModifier;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import ua.krasnyanskiy.jrsh.operation.EvaluationResult;
import ua.krasnyanskiy.jrsh.operation.EvaluationResult.ResultCode;
import ua.krasnyanskiy.jrsh.operation.Operation;
import ua.krasnyanskiy.jrsh.operation.parser.LL1OperationParser;

import java.io.IOException;
import java.util.concurrent.Callable;

import static org.mockito.Mockito.times;

/**
 * Unit tests for {@link ShellEvaluationStrategy}
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ShellEvaluationStrategy.class, ConsoleReader.class})
public class ShellEvaluationStrategyTest {

    @Spy private ShellEvaluationStrategy strategySpy;

    @Mock private Operation loginMock;
    @Mock private Operation exportMock;
    @Mock private LL1OperationParser parserMock;
    @Mock private Callable<EvaluationResult> loginTaskMock;
    @Mock private Callable<EvaluationResult> exportTaskMock;
    @Mock private EvaluationResult loginResultMock;
    @Mock private EvaluationResult exportResultMock;
    @Mock private ConsoleReader consoleMock;

    public ShellEvaluationStrategyTest() throws IOException {
        strategySpy = new ShellEvaluationStrategy();
    }

    @Before
    public void before() throws IOException {
        MemberModifier.suppress(MemberMatcher.constructor(ShellEvaluationStrategy.class));
        MemberModifier.suppress(MemberMatcher.method(ConsoleReader.class, "println", CharSequence.class));
        MockitoAnnotations.initMocks(this);

        Whitebox.setInternalState(strategySpy, "console", consoleMock);
        Whitebox.setInternalState(strategySpy, "parser", parserMock);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void should() throws Exception {

        /** Given **/
        final String USER_LOGIN_INPUT = "superuser%superuser@localhost:8080/jasperserver-pro";
        final String LOGIN_RESULT_MESSAGE = "I'm fine, thanks!";
        final String USER_EXPORT_INPUT = "export repository /public/Samples";
        final String EXPORT_RESULT_MESSAGE = "I'm fine too, thanks!";

        Mockito.when(parserMock.parse(USER_LOGIN_INPUT)).thenReturn(loginMock);
        Mockito.when(parserMock.parse(USER_EXPORT_INPUT)).thenReturn(exportMock);
        Mockito.when(loginMock.eval()).thenReturn(loginTaskMock);
        Mockito.when(exportMock.eval()).thenReturn(exportTaskMock);
        Mockito.when(loginTaskMock.call()).thenReturn(loginResultMock);
        Mockito.when(exportTaskMock.call()).thenReturn(exportResultMock);
        Mockito.when(loginResultMock.getCode()).thenReturn(ResultCode.SUCCESS);
        Mockito.when(loginResultMock.getMessage()).thenReturn(LOGIN_RESULT_MESSAGE);
        Mockito.when(exportResultMock.getMessage()).thenReturn(EXPORT_RESULT_MESSAGE);
        Mockito.when(consoleMock.readLine()).thenReturn(USER_EXPORT_INPUT);
        Mockito.doNothing().doThrow(new IllegalArgumentException("exit_flag")).when(consoleMock).flush();

        /** When **/
        try {
            strategySpy.eval(new String[]{USER_LOGIN_INPUT});
        } catch (Exception e) {
            // Then
            // (we should use the exit flag to stop infinite loop and verify result)
            Assertions.assertThat(e.getMessage()).isEqualTo("exit_flag");
        }

        /** Then **/
        Mockito.verify(parserMock, times(1)).parse(USER_LOGIN_INPUT);
        Mockito.verify(parserMock, times(1)).parse(USER_EXPORT_INPUT);
        Mockito.verify(loginMock, times(1)).eval();
        Mockito.verify(exportMock, times(1)).eval();
        Mockito.verify(loginTaskMock, times(1)).call();
        Mockito.verify(exportTaskMock, times(1)).call();
        Mockito.verify(loginResultMock, times(1)).getCode();
        Mockito.verify(loginResultMock, times(1)).getMessage();
        Mockito.verify(consoleMock, times(2)).flush();
    }

    @After
    public void after() {
        Mockito.reset(loginMock, exportMock, parserMock, loginTaskMock, exportTaskMock,
                loginResultMock, exportResultMock, consoleMock, strategySpy);
    }
}