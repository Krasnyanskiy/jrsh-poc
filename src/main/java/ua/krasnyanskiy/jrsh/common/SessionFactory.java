package ua.krasnyanskiy.jrsh.common;

import com.jaspersoft.jasperserver.jaxrs.client.core.AuthenticationCredentials;
import com.jaspersoft.jasperserver.jaxrs.client.core.RestClientConfiguration;
import com.jaspersoft.jasperserver.jaxrs.client.core.Session;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import lombok.NonNull;

/**
 * Used to build REST Client session.
 *
 * @author Alexander Krasnyanskiy
 * @since 1.0
 */
public class SessionFactory {

    private static Session SHARED_SESSION;

    /**
     * Returns shared session
     *
     * @return session
     */
    public static Session getSharedSession() {
        return SHARED_SESSION;
    }

    /**
     * Creates a new nonshared session
     *
     * @param url          server URL
     * @param username     username
     * @param password     password
     * @param organization organization
     * @return non shared session
     */
    public static Session createNewSession(@NonNull String url, @NonNull String username,
                                           @NonNull String password, String organization) {
        return createSession(url, username, password, organization);
    }

    /**
     * Creates a new session and shares it among consumers
     *
     * @param url          server URL
     * @param username     username
     * @param password     password
     * @param organization organization
     * @return shared session
     */
    public static Session createSharedSession(@NonNull String url, @NonNull String username,
                                              @NonNull String password, String organization) {
        return SHARED_SESSION = createSession(url, username, password, organization);
    }

    /**
     * Makes REST call and returns created brand new session
     *
     * @param url          server URL
     * @param username     username
     * @param password     password
     * @param organization organization
     * @return session
     */
    private static Session createSession(@NonNull String url, @NonNull String username,
                                         @NonNull String password, String organization) {
        username = organization == null ? username : username.concat("|").concat(organization);
        return new Session(
                new SessionStorage(
                        new RestClientConfiguration(url),
                        new AuthenticationCredentials(username, password)
                )
        );
    }

}
