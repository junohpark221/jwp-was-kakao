package webserver.http.request.contents;

import java.util.Map;

public class RequestUserInfo {
    private final String userId;
    private final String password;
    private final String userName;
    private final String email;

    public RequestUserInfo(Map<String, String> contents) {
        this.userId = contents.getOrDefault("userId", null);
        this.password = contents.getOrDefault("password", null);
        this.userName = contents.getOrDefault("name", null);
        this.email = contents.getOrDefault("email", null);
    }

    public String getUserId() {
        return this.userId;
    }

    public String getPassword() {
        return this.password;
    }

    public String getUserName() {
        return this.userName;
    }

    public String getEmail() {
        return this.email;
    }
}
