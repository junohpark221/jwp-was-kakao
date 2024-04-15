package webserver.http;

import webserver.controller.RequestController;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;

public class RequestMappingHandler {
    private final HttpRequest httpRequest;
    private final HttpResponse httpResponse;

    public RequestMappingHandler(BufferedReader br, DataOutputStream dos) throws IOException {
        this.httpRequest = new HttpRequest(br);
        this.httpResponse = new HttpResponse(dos);
    }

    public void run() {
        RequestController requestController = matchController(httpRequest);
        requestController.doService(httpRequest, httpResponse);
    }

    private RequestController matchController(HttpRequest httpRequest) {
        RequestController resourceController = ResourceMapper.getController(httpRequest.getFileExtension());
        if (resourceController != null) {
            return resourceController;
        }

        return ServiceMapper.getController(httpRequest.getPath());
    }
}
