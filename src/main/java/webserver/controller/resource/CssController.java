package webserver.controller.resource;

import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;
import webserver.controller.RequestController;

public class CssController extends RequestController {
    @Override
    public void doService(HttpRequest httpRequest, HttpResponse httpResponse) {
        super.doService(httpRequest, httpResponse);
    }

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        httpResponse.responseResource(httpRequest.getPath(), ResourceType.CSS);
    }
}
