package webserver.controller.resource;

import webserver.http.HttpRequest;
import webserver.http.HttpResponse;
import webserver.controller.RequestController;

public class HtmlController extends RequestController {
    @Override
    public void doService(HttpRequest httpRequest, HttpResponse httpResponse) {
        super.doService(httpRequest, httpResponse);
    }

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        System.out.println(httpRequest.getPath());
        httpResponse.responseResource(httpRequest.getPath(), ResourceType.HTML);
    }
}
