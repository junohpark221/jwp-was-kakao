package webserver.http;

import webserver.controller.RequestController;
import webserver.controller.resource.*;

import java.util.HashMap;
import java.util.Map;

public class ResourceMapper {
    private static final Map<String, RequestController> resourceControllers;

    static {
        resourceControllers = new HashMap<>();
        resourceControllers.put("html", new HtmlController());
        resourceControllers.put("css", new CssController());
        resourceControllers.put("ico", new IcoController());
        resourceControllers.put("js", new JsController());
        resourceControllers.put("woff", new FontController());
        resourceControllers.put("ttf", new FontController());
    }

    public static RequestController getController(String extension) {
        return resourceControllers.getOrDefault(extension, null);
    }
}
