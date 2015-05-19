package com.jaspersoft.jasperserver.jrsh.core;

import com.jaspersoft.jasperserver.jaxrs.client.core.AuthenticationCredentials;
import com.jaspersoft.jasperserver.jaxrs.client.core.RestClientConfiguration;
import com.jaspersoft.jasperserver.jaxrs.client.core.Session;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import lombok.NonNull;

public class SessionFactory {
    private static Session SHARED_SESSION;

    /**
     * Returns the shared session.
     *
     * @return session
     */
    public static Session getSharedSession() {
        //checkState(SHARED_SESSION != null, "Session cannot be empty.");
        return SHARED_SESSION;
    }

    /**
     * Creates a new unshared session.
     *
     * @param url          server URL
     * @param username     username
     * @param password     password
     * @param organization organization
     * @return non shared session
     */
    public static Session createUnsharedSession(@NonNull String url, @NonNull String username,
                                                @NonNull String password, String organization) {
        return createSession(url, username, password, organization);
    }

    /**
     * Creates a new session and shares it among consumers.
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
     * Makes REST call and returns created brand new session.
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
        url = url.startsWith("http") ? url : "http://".concat(url);
        return new Session(
                new SessionStorage(
                        new RestClientConfiguration(url),
                        new AuthenticationCredentials(username, password)
                )
        );
    }
}
