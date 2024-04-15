package webserver.controller;

import webserver.http.request.HttpRequest;
import webserver.http.HttpResponse;

public abstract class RequestController {
    private static final String INDEX_HTML_PATH = "/index.html";

    public void doService(HttpRequest httpRequest, HttpResponse httpResponse) {
        String method = httpRequest.getMethod();
        if (method.equals("GET")) {
            doGet(httpRequest, httpResponse);
            return;
        }

        doPost(httpRequest, httpResponse);
    }

    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        httpResponse.redirect(INDEX_HTML_PATH);
    }

    public void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        httpResponse.redirect(INDEX_HTML_PATH);
    }
}
