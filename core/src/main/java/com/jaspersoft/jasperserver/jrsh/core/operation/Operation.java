package com.jaspersoft.jasperserver.jrsh.core.operation;

import com.jaspersoft.jasperserver.jaxrs.client.core.Session;

/**
 * @author Alex Krasnyanskiy
 */
public interface Operation {

    OperationResult eval(Session session);

}