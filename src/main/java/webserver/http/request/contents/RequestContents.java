package webserver.http.request.contents;

import utils.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class RequestContents {
    private static final String REQUEST_LINE_KEY = "Request-Line";
    private static final String CONTENT_LENGTH_KEY = "Content-Length";

    private final RequestUserInfo requestUserInfo;
    private final Map<String, String> contents;

    public RequestContents(BufferedReader br, Map<String, String> request) throws IOException {
        Map<String, String> contents = extractContents(br, request);
        this.requestUserInfo = new RequestUserInfo(contents);
        this.contents = contents;
    }

    private Map<String, String> extractContents(BufferedReader br, Map<String, String> request) throws IOException {
        int contentLength = Integer.parseInt(request.getOrDefault(CONTENT_LENGTH_KEY, String.valueOf(0)));
        if (contentLength > 0) {
            return extractFromBody(br, contentLength);
        }

        return extractFromQueryParameters(request.get(REQUEST_LINE_KEY));
    }

    private Map<String, String> extractFromBody(BufferedReader br, int contentLength) throws IOException {
        String contents = IOUtils.readData(br, contentLength);
        return parseContents(contents);
    }

    private Map<String, String> extractFromQueryParameters(String requestLine) {
        String url = extractUrl(requestLine);
        String[] splits = url.split("\\?");
        if (splits.length < 2) {
            return new HashMap<>();
        }

        return parseContents(url.split("\\?")[1]);
    }

    private String extractUrl(String requestLine) {
        if (requestLine == null) {
            return "";
        }
        return requestLine.split(" ")[1];
    }

    private Map<String, String> parseContents(String contents) {
        Map<String, String> queryParameters = new HashMap<>();
        contents = URLDecoder.decode(contents, StandardCharsets.UTF_8);

        for (String queryParameter : contents.split("&")) {
            String[] splitedQueryParameter = queryParameter.split("=");
            queryParameters.put(splitedQueryParameter[0], splitedQueryParameter[1]);
        }
        return queryParameters;
    }

    public RequestUserInfo getRequestUserInfo() {
        return this.requestUserInfo;
    }
}
