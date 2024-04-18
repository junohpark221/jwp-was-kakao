package webserver.http.request;

import java.util.HashMap;
import java.util.Map;

public class Cookie {
    private final Map<String, String> contents;

    public Cookie(Map<String, String> request) {
        this.contents = new HashMap<>();
        extractCookie(request);
    }

    private void extractCookie(Map<String, String> request) {
        String cookieFromHeader = request.getOrDefault("Cookie", "");

        if (cookieFromHeader.isEmpty()) {
            return;
        }

        for (String splits : cookieFromHeader.split("; ")) {
            String[] keyValue = splits.split("=");
            putDataIntoCookie(keyValue);
        }
    }

    private void putDataIntoCookie(String[] keyValue) {
        if (keyValue.length == 2) {
            contents.put(keyValue[0], keyValue[1]);
        }
    }

    public String getCookieSessionId() {
        return this.contents.getOrDefault("JSESSIONID", null);
    }

    public String getCookieIsLogin() {
        return this.contents.getOrDefault("logined", "false");
    }

    public Map<String, String> getContents() {
        return contents;
    }
}
