package model;

import db.DataBase;

import java.util.HashMap;
import java.util.Map;

public class Users {
    private final Map<String, Object> values;

    public Users() {
        this.values = extractUsersFromDB();
    }

    private Map<String, Object> extractUsersFromDB() {
        Map<String, Object> users = new HashMap<>();
        users.put("users", DataBase.findAllUser());
        return users;
    }

    public Map<String, Object> getValues() {
        return this.values;
    }
}
