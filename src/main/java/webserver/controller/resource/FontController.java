package webserver.controller.resource;

import webserver.http.request.HttpRequest;
import webserver.http.HttpResponse;
import webserver.controller.RequestController;

import java.util.HashMap;
import java.util.Map;

public class FontController extends RequestController {
    private static Map<String, ResourceType> fonts;

    static {
        fonts = new HashMap<>();
        fonts.put("woff", ResourceType.FONT_WOFF);
        fonts.put("ttf", ResourceType.FONT_TTF);
    }

    @Override
    public void doService(HttpRequest httpRequest, HttpResponse httpResponse) {
        super.doService(httpRequest, httpResponse);
    }

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        httpResponse.responseResource(
                httpRequest.getPath(), fonts.getOrDefault(httpRequest.getFileExtension(), ResourceType.FONT_WOFF)
        );
    }
}
