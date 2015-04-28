package ua.krasnyanskiy.jrsh.common;

import lombok.Data;

import java.util.Map;

/**
 * @author Alexander Krasnyanskiy
 */
@Data
public class Response {
    private boolean success;
    private Map<String, Boolean> lookups;
}
