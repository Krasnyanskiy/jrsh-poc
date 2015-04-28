package ua.krasnyanskiy.jrsh.common;

import com.jaspersoft.jasperserver.jaxrs.client.core.Session;

/**
 * @author Alexander Krasnyanskiy
 */
public class Connector {
    public Session connect() {
        return SessionFactory.getSharedSession();
    }
}
