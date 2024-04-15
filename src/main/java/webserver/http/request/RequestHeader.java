package webserver.http.request;

import java.util.Map;

public class RequestHeader {
    private static final String REQUEST_LINE_KEY = "Request-Line";
    private final Map<String, String> header;

    public RequestHeader(Map<String, String> request) {
        this.header = extractHeader(request);
    }

    private Map<String, String> extractHeader(Map<String, String> request) {
        request.remove(REQUEST_LINE_KEY);
        return request;
    }

    public Map<String, String> getHeader() {
        return this.header;
    }
}
