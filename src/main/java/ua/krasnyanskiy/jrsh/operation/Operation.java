package ua.krasnyanskiy.jrsh.operation;

import ua.krasnyanskiy.jrsh.operation.grammar.Grammar;
import ua.krasnyanskiy.jrsh.operation.parameter.OperationParameters;

import java.util.concurrent.Callable;

/**
 * Base interface for all operations in the application.
 *
 * @author Alexander Krasnyanskiy
 * @since 1.0
 */
public interface Operation<R, E extends OperationParameters> extends ConsoleAware {

    Callable<R> perform(E parameters);

    Grammar getGrammar();

    String getDescription();

    Class<E> getParametersType();
}
