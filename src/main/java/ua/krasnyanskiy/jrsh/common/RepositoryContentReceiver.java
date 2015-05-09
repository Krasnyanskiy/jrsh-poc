package ua.krasnyanskiy.jrsh.common;

import com.jaspersoft.jasperserver.dto.resources.ClientResourceLookup;
import com.jaspersoft.jasperserver.jaxrs.client.core.Session;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.BadRequestException;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.IllegalParameterValueException;
import com.jaspersoft.jasperserver.jaxrs.client.core.exceptions.ResourceNotFoundException;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.ResourceSearchParameter.FOLDER_URI;
import static com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.ResourceSearchParameter.LIMIT;
import static com.jaspersoft.jasperserver.jaxrs.client.apiadapters.resources.ResourceSearchParameter.RECURSIVE;

/**
 * @author Alexander Krasnyanskiy
 */
public class RepositoryContentReceiver {

    /**
     * Downloads resources.
     *
     * @param path resource identifier
     * @return content
     */
    public Response receive(String path) {
        Map<String, Boolean> lookups = new HashMap<>();
        Response resp = new Response();
        try {
            List<ClientResourceLookup> list = getResourceLookups(path);
            for (ClientResourceLookup lookup : list) {
                String uri = lookup.getUri();
                String resType = lookup.getResourceType();
                lookups.put(uri, resType.equals("folder"));
            }
            resp.setSuccess(true);
        } catch (IllegalParameterValueException | NullPointerException | BadRequestException | ResourceNotFoundException e1) {
            resp.setSuccess(false);
            try {
                if (e1 instanceof ResourceNotFoundException) {
                    String cut = path.substring(path.lastIndexOf("/"));
                    path = StringUtils.removeEnd(path, cut);
                    if ("".equals(path)) {
                        path = "/";
                    }
                    List<ClientResourceLookup> reobtained = getResourceLookups(path);
                    for (ClientResourceLookup lookup : reobtained) {
                        String uri = lookup.getUri();
                        String resType = lookup.getResourceType();
                        lookups.put(uri, resType.equals("folder"));
                    }
                }
            } catch (IllegalParameterValueException | NullPointerException | BadRequestException | ResourceNotFoundException ignored) {
            }
        }
        resp.setLookups(lookups);
        return resp;
    }


    public boolean isSessionAccessible() {
        try {
            new Connector().connect();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Searches for only folders.
     *
     * @param path folder URI
     * @return resources
     */
    protected List<ClientResourceLookup> getResourceLookups(String path) {
        return new Connector()
                .connect()
                .resourcesService()
                .resources()
                .parameter(FOLDER_URI, path)
                .parameter(RECURSIVE, "false")
                .search()
                .getEntity()
                .getResourceLookups();
    }

    public List<String> receiveAsList() {
        List<String> list = new ArrayList<>();
        List<ClientResourceLookup> lookups = new Connector().connect()
                .resourcesService()
                .resources()
                .parameter(FOLDER_URI, "/")
                .parameter(LIMIT, "2500")
                .search()
                .getEntity()
                .getResourceLookups();
        for (ClientResourceLookup lookup : lookups) {
            list.add(lookup.getUri());
        }
        return list;
    }

    @Data
    public static class Response {
        private boolean success;
        private Map<String, Boolean> lookups;
    }

    public static class Connector {
        public Session connect() {
            return SessionFactory.getSharedSession();
        }
    }

}
