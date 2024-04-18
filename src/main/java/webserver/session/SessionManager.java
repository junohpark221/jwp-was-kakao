package webserver.session;

import webserver.http.request.HttpRequest;

import java.util.HashMap;
import java.util.Map;

public class SessionManager {
    private static final Map<String, Session> SESSIONS = new HashMap<>();

    public static void add(final Session session) {
        SESSIONS.put(session.getId(), session);
    }

    public static Session findSession(final String id) {
        return SESSIONS.getOrDefault(id, null);
    }

    public static void remove(final String id) {
        SESSIONS.remove(id);
    }

    public static boolean isLogin(HttpRequest httpRequest) {
        String sessionId = httpRequest.getCookieSessionId();
        return (SessionManager.findSession(sessionId) != null);
    }
}
