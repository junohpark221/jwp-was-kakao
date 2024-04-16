package webserver.session;

import java.util.HashMap;
import java.util.Map;

public class Session {
    private final String id;
    private final Map<String, Object> values;

    public Session(final String id) {
        this.id = id;
        this.values = new HashMap<>();
    }

    public String getId() {
        return this.id;
    }

    public Object getAttribute(final String name) {
        return this.values.getOrDefault(name, null);
    }

    public void setAttribute(final String name, final Object value) {
        this.values.put(name, value);
    }

    public void removeAttribute(final String name) {
        this.values.remove(name);
    }

    public void invalidate() {
        this.values.clear();
    }
}
