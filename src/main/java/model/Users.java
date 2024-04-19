package model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Users {
    private final Map<String, Object> values;

    public Users(Collection<User> allUser) {
        this.values = extractUsersFromDB(allUser);
    }

    private Map<String, Object> extractUsersFromDB(Collection<User> allUser) {
        Map<String, Object> users = new HashMap<>();
        users.put("users", allUser);
        return users;
    }

    public Map<String, Object> getValues() {
        return this.values;
    }
}
