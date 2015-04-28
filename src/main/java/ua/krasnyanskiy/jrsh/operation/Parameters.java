package ua.krasnyanskiy.jrsh.operation;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unchecked")
public class Parameters {

    private Map<String, Object> params = new HashMap<>();

    public void put(String key, String value) {
        params.put(key, value);
    }

    public <T> T get(String key) {
        return (T) params.get(key);
    }

    public boolean isEmpty() {
        return params.isEmpty();
    }

}
