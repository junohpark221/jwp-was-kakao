package webserver.http.request;

import java.util.Map;

public class RequestLine {
    private static final String REQUEST_LINE_KEY = "Request-Line";
    private final String method;
    private final String url;

    public RequestLine(Map<String, String> request) {
        this.method = extractMethod(request.get(REQUEST_LINE_KEY));
        this.url = extractUrl(request.get(REQUEST_LINE_KEY));
    }

    private String extractMethod(String requestLine) {
        return requestLine.split(" ")[0];
    }

    private String extractUrl(String requestLine) {
        if (requestLine == null) {
            return "";
        }
        return requestLine.split(" ")[1];
    }

    public String getMethod() {
        return this.method;
    }

    public String getUrl() {
        return this.url;
    }
}
