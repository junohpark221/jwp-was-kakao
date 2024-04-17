package webserver.http.request;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class CookieTest {
    @Test
    public void 생성자_테스트() {
        Map<String, String> request = new HashMap<>();
        request.put("Cookie", "JSESSIONID=abcd-1234-efgh-5678; logined=true");

        Cookie cookie = new Cookie(request);

        assertAll(
                () -> assertThat(cookie.getCookieSessionId()).isEqualTo("abcd-1234-efgh-5678"),
                () -> assertThat(cookie.getCookieIsLogin()).isEqualTo("true")
        );
    }
}
