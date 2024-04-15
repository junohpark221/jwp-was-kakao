package webserver.http;

import webserver.controller.DefaultRequestController;
import webserver.controller.RequestController;
import webserver.controller.user.UserCreateController;

import java.util.HashMap;
import java.util.Map;

public class ServiceMapper {
    private static final Map<String, RequestController> serviceControllers;

    static {
        serviceControllers = new HashMap<>();
        serviceControllers.put("/user/create", new UserCreateController());
    }

    public static RequestController getController(String url) {
        return serviceControllers.getOrDefault(url, new DefaultRequestController());
    }
}
