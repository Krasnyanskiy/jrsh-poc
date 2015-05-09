package ua.krasnyanskiy.jrsh.common;

import com.jaspersoft.jasperserver.jaxrs.client.core.AuthenticationCredentials;
import com.jaspersoft.jasperserver.jaxrs.client.core.RestClientConfiguration;
import com.jaspersoft.jasperserver.jaxrs.client.core.Session;
import com.jaspersoft.jasperserver.jaxrs.client.core.SessionStorage;
import lombok.NonNull;

/**
 * Used for REST Client session building.
 *
 * @author Alexander Krasnyanskiy
 * @since 1.0
 */
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

    /**
     * Useful {@link Session} builder. It allows you to simplify the process
     * of creating the session.
     *
     * @author Alexander Krasnyanskiy
     * @see 1.0
     */
    public static class SessionBuilder {

        private String server, username, password, organization;

        public SessionBuilder withServer(String server) {
            this.server = server;
            return this;
        }

        public SessionBuilder withUsername(String username) {
            this.username = username;
            return this;
        }

        public SessionBuilder withPassword(String password) {
            this.password = password;
            return this;
        }

        public SessionBuilder withOrganization(String organization) {
            this.organization = organization;
            return this;
        }

        public Session buildSharedSession(){
            SHARED_SESSION = createSharedSession(server, username,
                    password, organization);
            return SHARED_SESSION;
        }

        public Session buildUnsharedSession(){
            return createSharedSession(server, username, password,
                    organization);
        }
    }
}
