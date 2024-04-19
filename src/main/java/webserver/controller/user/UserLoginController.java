package webserver.controller.user;

import db.DataBase;
import model.User;
import webserver.controller.RequestController;
import webserver.controller.resource.ResourceType;
import webserver.http.request.HttpRequest;
import webserver.http.request.contents.RequestContents;
import webserver.http.request.contents.RequestUserInfo;
import webserver.http.response.HttpResponse;
import webserver.session.Session;
import webserver.session.SessionManager;

import java.util.UUID;

public class UserLoginController extends RequestController {
    private static final String INDEX_HTML_PATH = "/index.html";
    private static final String LOGIN_HTML_PATH = "/user/login.html";
    private static final String LOGIN_FAILED_HTML = "/user/login_failed.html";

    @Override
    public void doService(HttpRequest httpRequest, HttpResponse httpResponse) {
        super.doService(httpRequest, httpResponse);
    }


    @Override
    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        try {
            RequestContents requestContents = httpRequest.getContents();
            RequestUserInfo requestUserInfo = requestContents.getRequestUserInfo();

            String userId = requestUserInfo.getUserId();
            String password = requestUserInfo.getPassword();

            User user = DataBase.findUserById(userId);

            checkLogin(user, password, httpRequest, httpResponse);
        } catch (NullPointerException e) {
            httpResponse.redirect(LOGIN_FAILED_HTML);
        }
    }

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        try {
            String sessionId = httpRequest.getCookieSessionId();
            Session session = SessionManager.findSession(sessionId);
            httpResponse.redirect(INDEX_HTML_PATH);
        } catch (NullPointerException e) {
            httpResponse.responseResource(LOGIN_HTML_PATH, ResourceType.HTML);
        }
    }

    private void checkLogin(User user, String password, HttpRequest httpRequest, HttpResponse httpResponse) {
        if (user.checkPassword(password)) {
            String sessionId = getCookieSessionId(httpRequest);

            httpResponse.addCookie(createSessionCookie(sessionId));
            httpResponse.addCookie(createLoginCookie());

            SessionManager.addUserToSession(sessionId, user);

            httpResponse.redirect(INDEX_HTML_PATH);
            return;
        }

        httpResponse.redirect(LOGIN_FAILED_HTML);
    }

    private String getCookieSessionId(HttpRequest httpRequest) {
        if (httpRequest.getCookieSessionId() == null) {
            return UUID.randomUUID().toString();
        }

        return httpRequest.getCookieSessionId();
    }

    private String createSessionCookie(String sessionId) {
        return "JSESSIONID=" + sessionId + "; Path=/";
    }

    private String createLoginCookie() {
        return "logined=true; Path=/";
    }


}
