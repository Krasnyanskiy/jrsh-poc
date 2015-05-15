package refactoring;

import com.jaspersoft.jasperserver.jaxrs.client.core.Session;
import refactoring.impl.OperationResult;

/**
 * Base interface for all operations in the application. It
 * should contain a static metadata of the operation
 * (i.e. grammar, parameter type, etc).
 *
 * @author Alexander Krasnyanskiy
 * @version 1.0
 */
public interface Operation {

    OperationResult eval(Session session);

}
