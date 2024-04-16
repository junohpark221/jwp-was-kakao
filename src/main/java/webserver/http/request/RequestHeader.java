package webserver.http.request;

import java.util.HashMap;
import java.util.Map;

public class RequestHeader {
    private static final String REQUEST_LINE_KEY = "Request-Line";
    private final Map<String, String> header;
    private final Map<String, String> cookie;

    public RequestHeader(Map<String, String> request) {
        this.header = extractHeader(request);
        this.cookie = extractCookie();
    }

    private Map<String, String> extractHeader(Map<String, String> request) {
        request.remove(REQUEST_LINE_KEY);
        return request;
    }

    private Map<String, String> extractCookie() {
        Map<String, String> cookies = new HashMap<>();
        String cookieFromHeader = this.header.getOrDefault("Cookie", "");

        if (cookieFromHeader.isEmpty()) {
            return cookies;
        }

        for (String splits : cookieFromHeader.split("; ")) {
            String[] keyValue = splits.split("=");
            cookies.put(keyValue[0], keyValue[1]);
        }

        this.header.remove("Cookie");

        return cookies;
    }

    public Map<String, String> getHeader() {
        return this.header;
    }

    public String getCookieSessionId() {
        return this.cookie.getOrDefault("JSESSIONID", "");
    }

    public String getCookieIsLogin() {
        return this.cookie.getOrDefault("logined", "false");
    }

    public Map<String, String> getCookie() {
        return cookie;
    }
}
