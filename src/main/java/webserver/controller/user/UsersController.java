package webserver.controller.user;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import db.DataBase;
import model.User;
import model.Users;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.controller.RequestController;
import webserver.controller.resource.ResourceType;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.session.SessionManager;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UsersController extends RequestController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UsersController.class);
    private static final String LOGIN_HTML_PATH = "/user/login.html";

    @Override
    public void doService(HttpRequest httpRequest, HttpResponse httpResponse) {
        super.doService(httpRequest, httpResponse);
    }

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        if (isLogin(httpRequest)) {
            render(httpResponse);
            return;
        }

        httpResponse.redirect(LOGIN_HTML_PATH);
    }

    private void render(HttpResponse httpResponse) {
        try {
            TemplateLoader loader = new ClassPathTemplateLoader();
            loader.setPrefix("/templates");
            loader.setSuffix(".html");
            Handlebars handlebars = new Handlebars(loader);

            Template template = handlebars.compile("/user/list");

            Users users = new Users();
            httpResponse.responseResource(template.apply(users.getValues()).getBytes(), ResourceType.HTML);
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }

    private boolean isLogin(HttpRequest httpRequest) {
        String sessionId = httpRequest.getCookieSessionId();
        return (SessionManager.findSession(sessionId) != null);
    }
}
