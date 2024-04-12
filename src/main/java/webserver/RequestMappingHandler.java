package webserver;

import webserver.controller.DefaultRequestController;
import webserver.controller.RequestController;
import webserver.controller.resource.*;
import webserver.controller.user.UserCreateController;
import webserver.http.HttpRequest;
import webserver.http.HttpResponse;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RequestMappingHandler {
    private static final Map<String, RequestController> resourceControllers;
    private static final Map<String, RequestController> serviceControllers;
    private final HttpRequest httpRequest;
    private final HttpResponse httpResponse;

    static {
        resourceControllers = new HashMap<>();
        resourceControllers.put("html", new HtmlController());
        resourceControllers.put("css", new CssController());
        resourceControllers.put("ico", new IcoController());
        resourceControllers.put("js", new JsController());
        resourceControllers.put("woff", new FontController());
        resourceControllers.put("ttf", new FontController());
    }

    static {
        serviceControllers = new HashMap<>();
        serviceControllers.put("/user/create", new UserCreateController());
    }


    public RequestMappingHandler(BufferedReader br, DataOutputStream dos) throws IOException {
        this.httpRequest = new HttpRequest(br);
        this.httpResponse = new HttpResponse(dos);
    }

    public void run() {
        RequestController requestController = matchController(httpRequest);
        requestController.doService(httpRequest, httpResponse);
    }

    private RequestController matchController(HttpRequest httpRequest) {
        RequestController resourceController = matchResourceController(httpRequest.getFileExtension());
        if (resourceController != null) {
            return resourceController;
        }

        return matchServiceController(httpRequest.getPath());
    }

    private RequestController matchResourceController(String extension) {
        return resourceControllers.getOrDefault(extension, null);
    }

    private RequestController matchServiceController(String url) {
        return serviceControllers.getOrDefault(url, new DefaultRequestController());
    }
}
