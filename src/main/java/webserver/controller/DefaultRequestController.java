package webserver.controller;

import webserver.http.request.HttpRequest;
import webserver.http.HttpResponse;

public class DefaultRequestController extends RequestController {
    @Override
    public void doService(HttpRequest httpRequest, HttpResponse httpResponse) {
        super.doService(httpRequest, httpResponse);
    }

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        super.doGet(httpRequest, httpResponse);
    }

    @Override
    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        super.doPost(httpRequest, httpResponse);
    }
}
