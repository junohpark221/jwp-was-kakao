package webserver.controller.user;

import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.http.request.HttpRequest;
import webserver.http.request.RequestContents;
import webserver.http.response.HttpResponse;
import webserver.controller.RequestController;

import java.util.Map;

public class UserCreateController extends RequestController {
    private static final String INDEX_HTML_PATH = "/index.html";
    private static final String FORM_HTML_PATH = "/user/form.html";
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpResponse.class);

    @Override
    public void doService(HttpRequest httpRequest, HttpResponse httpResponse) {
        super.doService(httpRequest, httpResponse);
    }

    @Override
    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        try {
            User user = createUser(httpRequest.getContents());
            DataBase.addUser(user);
            LOGGER.info(DataBase.findAllUser().toString());
            httpResponse.redirect(INDEX_HTML_PATH);
        } catch (IllegalArgumentException e) {
            httpResponse.redirect(FORM_HTML_PATH);
        }
    }

    private User createUser(RequestContents requestContents) {
        validateUserInfo(requestContents.extractUserId());
        return new User(
                requestContents.extractUserId(), requestContents.extractPassword(), requestContents.extractUserName(), requestContents.extractEmail()
        );
    }

    public void validateUserInfo(String userId) {
        if (DataBase.findUserById(userId) != null) {
            throw new IllegalArgumentException("기존에 존재하는 user ID 입니다.");
        }
    }
}
