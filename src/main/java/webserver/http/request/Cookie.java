package webserver.http.request;

import java.util.HashMap;
import java.util.Map;

public class Cookie {
    private final Map<String, String> contents;

    public Cookie(Map<String, String> request) {
        this.contents = extractCookie(request);
    }

    private Map<String, String> extractCookie(Map<String, String> request) {
        Map<String, String> cookies = new HashMap<>();
        String cookieFromHeader = request.getOrDefault("Cookie", "");

        if (cookieFromHeader.isEmpty()) {
            return cookies;
        }

        for (String splits : cookieFromHeader.split("; ")) {
            String[] keyValue = splits.split("=");
            cookies.put(keyValue[0], keyValue[1]);
        }

        return cookies;
    }

    public String getCookieSessionId() {
        return this.contents.getOrDefault("JSESSIONID", "");
    }

    public String getCookieIsLogin() {
        return this.contents.getOrDefault("logined", "false");
    }

    public Map<String, String> getContents() {
        return contents;
    }
}
