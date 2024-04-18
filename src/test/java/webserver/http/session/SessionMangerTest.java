package webserver.http.session;

import model.User;
import org.junit.jupiter.api.Test;
import webserver.session.Session;
import webserver.session.SessionManager;

import static org.assertj.core.api.Assertions.assertThat;

public class SessionMangerTest {
    @Test
    public void 세션_추가_및_제거_테스트() {
        Session session01 = new Session("id01");
        Session session02 = new Session("id02");

        SessionManager.add(session01);
        SessionManager.add(session02);

        assertThat(SessionManager.findSession("id01")).isEqualTo(session01);
        assertThat(SessionManager.findSession("id02")).isEqualTo(session02);

        SessionManager.remove("id02");

        assertThat(SessionManager.findSession("id02")).isNull();
    }

    @Test
    public void 세션_내_유저_추가_테스트() {
        User user = new User("userId", "password", "name", "email@email.com");

        SessionManager.addUserToSession("sessionId", user);
        Session session = SessionManager.findSession("sessionId");

        assertThat(session.getAttribute("sessionId")).isEqualTo(user);
    }
}
