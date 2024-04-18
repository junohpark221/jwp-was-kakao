package webserver.http.session;

import org.junit.jupiter.api.Test;
import webserver.session.Session;

import static org.assertj.core.api.Assertions.assertThat;

public class SessionTest {
    @Test
    public void 생성자_테스트() {
        Session session = new Session("id");

        assertThat(session.getId()).isEqualTo("id");
    }

    @Test
    public void 세션_attribute_테스트() {
        Session session = new Session("id");
        session.setAttribute("name01", "value01");
        session.setAttribute("name02", "value02");

        assertThat(session.getAttribute("name01")).isEqualTo("value01");

        session.removeAttribute("name02");
        assertThat(session.getAttribute("name02")).isNull();

        session.invalidate();
        assertThat(session.getAttribute("name01")).isNull();
    }
}
