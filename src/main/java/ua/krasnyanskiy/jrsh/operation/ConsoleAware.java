package ua.krasnyanskiy.jrsh.operation;

import jline.console.ConsoleReader;

/**
 * Interface to be implemented by any object that wishes
 * to be notified of the {@link ConsoleReader}.
 * <p/>
 * Implementing this interface makes sense for example
 * when an operation requires access to the console to print
 * some message.
 *
 * @author Alexander Krasnyanskiy
 * @since 1.0
 */
public interface ConsoleAware {

    void setConsole(ConsoleReader console);

}
