package ua.krasnyanskiy.jrsh.operation;

import lombok.NonNull;
import ua.krasnyanskiy.jrsh.operation.grammar.Grammar;

import java.util.concurrent.Callable;

/**
 * Base interface for all operations in the application.
 *
 * @author Alexander Krasnyanskiy
 */
public interface Operation extends ParameterParser, ConsoleAware {

    <R> Callable<R> perform(@NonNull Parameters parameters); // String? Really?

    Grammar getGrammar();

    String getDescription();
}
