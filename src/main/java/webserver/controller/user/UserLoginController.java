package webserver.controller.user;

import db.DataBase;
import model.User;
import webserver.controller.RequestController;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

import java.util.Map;
import java.util.UUID;

public class UserLoginController extends RequestController {
    private static final String INDEX_HTML_PATH = "/index.html";
    private static final String LOGIN_FAILED_HTML = "/user/login_failed.html";

    @Override
    public void doService(HttpRequest httpRequest, HttpResponse httpResponse) {
        super.doService(httpRequest, httpResponse);
    }


    @Override
    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        try {
            Map<String, String> loginInfo = httpRequest.getContents();

            String userId = loginInfo.getOrDefault("userId", null);
            String password = loginInfo.getOrDefault("password", null);
            User user = DataBase.findUserById(userId);

            checkPassword(user, password, httpRequest, httpResponse);
        } catch (NullPointerException e) {
            httpResponse.redirect(LOGIN_FAILED_HTML);
        }
    }

    private void checkPassword(User user, String password, HttpRequest httpRequest, HttpResponse httpResponse) {
        if (user.getPassword().equals(password)) {
            httpResponse.addCookie("JSESSIONID", getCookieSessionId(httpRequest));
            httpResponse.addCookie("Path", "/");
            httpResponse.redirect(INDEX_HTML_PATH);
            return;
        }

        httpResponse.redirect(LOGIN_FAILED_HTML);
    }

    private String getCookieSessionId(HttpRequest httpRequest) {
        if (httpRequest.getCookieSessionId().isEmpty()) {
            return UUID.randomUUID().toString();
        }

        return httpRequest.getCookieSessionId();
    }
}
