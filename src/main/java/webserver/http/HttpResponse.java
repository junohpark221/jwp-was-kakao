package webserver.http;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import utils.FileIoUtils;
import webserver.controller.resource.ResourceType;

public class HttpResponse {
	private static final Logger LOGGER = LoggerFactory.getLogger(HttpResponse.class);
	private DataOutputStream dos;

	public HttpResponse(DataOutputStream dos) {
		this.dos = dos;
	}

	public void responseResource(String path, ResourceType resourceType) {
		try {
			dos.writeBytes(String.format("HTTP/1.1 %s \r\n", HttpStatus.OK));
			dos.writeBytes(String.format("Content-Type: %s\r\n", resourceType.getContentType()));

			byte[] body = getBody(resourceType.getLocation() + path);
			dos.writeBytes(String.format("Content-Length: %d\r\n", body.length));
			dos.writeBytes("\r\n");
			dos.write(body, 0, body.length);
			dos.flush();
		} catch (IOException | URISyntaxException e) {
			LOGGER.error(e.getMessage());
		}
	}

	public void redirect(String path) {
		try {
			dos.writeBytes(String.format("HTTP/1.1 %s \r\n", HttpStatus.FOUND.toString()));
			dos.writeBytes(String.format("Location: %s\r\n", path));
			dos.writeBytes("\r\n");
			dos.flush();
		} catch (IOException e) {
			LOGGER.error(e.getMessage());
		}
	}

	public byte[] getBody(String path) throws IOException, URISyntaxException {
		return FileIoUtils.loadFileFromClasspath(path);
	}
}
