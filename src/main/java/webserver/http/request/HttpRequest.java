package webserver.http.request;

import webserver.http.request.contents.RequestContents;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
	private static final String REQUEST_LINE_KEY = "Request-Line";

	private final RequestLine requestLine;
	private final RequestHeader requestHeader;
	private final RequestContents requestContents;
	private final Cookie cookie;

	public HttpRequest(BufferedReader br) throws IOException {
		Map<String, String> request = getRequestHeader(br);
		this.requestLine = new RequestLine(request);
		this.requestHeader = new RequestHeader(request);
		this.requestContents = new RequestContents(br, request);
		this.cookie = new Cookie(request);
	}

	private Map<String, String> getRequestHeader(BufferedReader br) throws IOException {
		Map<String, String> header = new HashMap<>();

		String line = br.readLine();
		header.put(REQUEST_LINE_KEY, line);

		line = br.readLine();
		while (!"".equals(line) && line != null) {
			String[] splits = line.split(": ");
			header.put(splits[0], splits[1]);
			line = br.readLine();
		}

		return header;
	}

	public String getFileExtension() {
		String[] splits = requestLine.getUrl().split("\\.");
		return splits[splits.length - 1];
	}

	public String getMethod() {
		return requestLine.getMethod();
	}

	public String getPath() {
		return extractPath();
	}

	private String extractPath() {
		return requestLine.getUrl().split("\\?")[0];
	}

	public Map<String, String> getHeader() {
		return requestHeader.getHeader();
	}

	public RequestContents getContents() {
		return this.requestContents;
	}

	public String getCookieSessionId() {
		return this.cookie.getCookieSessionId();
	}

	public boolean isLogin() {
		return Boolean.parseBoolean(this.cookie.getCookieIsLogin());
	}
}
