package webserver.http.request;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class RequestLineTest {
    @Test
    public void 생성자_테스트() {
        Map<String, String> request = new HashMap<>();
        request.put("Request-Line", "GET /user/login.html HTTP/1.1");
        request.put("Host", "localhost:8080");
        request.put("Connection", "keep-alive");

        RequestLine requestLine = new RequestLine(request);

        assertAll(
                () -> assertThat(requestLine.getMethod()).isEqualTo("GET"),
                () -> assertThat(requestLine.getUrl()).isEqualTo("/user/login.html")
        );
    }
}
