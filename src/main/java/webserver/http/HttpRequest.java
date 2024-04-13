package webserver.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import utils.IOUtils;

public class HttpRequest {
	private final String method;
	private final String url;
	private final Map<String, String> header;
	private final Map<String, String> contents;

	public HttpRequest(BufferedReader br) throws IOException {
		Map<String, String> request = getRequestHeader(br);
		this.method = extractMethod(request.get("Request-Line"));
		this.url = extractUrl(request.get("Request-Line"));
		this.header = extractHeader(request);
		this.contents = extractContents(br, request);
	}

	private Map<String, String> getRequestHeader(BufferedReader br) throws IOException {
		Map<String, String> header = new HashMap<>();

		String line = br.readLine();
		header.put("Request-Line", line);

		line = br.readLine();
		while (!"".equals(line) && line != null) {
			String[] splitedLine = line.split(": ");
			header.put(splitedLine[0], splitedLine[1]);
			line = br.readLine();
		}

		return header;
	}

	private String extractMethod(String requestLine) {
		return requestLine.split(" ")[0];
	}

	private String extractUrl(String requestLine) {
		if (requestLine == null) {
			return "";
		}
		return requestLine.split(" ")[1];
	}

	private Map<String, String> extractHeader(Map<String, String> request) {
		request.remove("Request-Line");
		return request;
	}

	private Map<String, String> extractContents(BufferedReader br, Map<String, String> request) throws IOException {
		int contentLength = Integer.parseInt(request.getOrDefault("Content-Length", String.valueOf(0)));
		if (contentLength > 0) {
			return extractFromBody(br, contentLength);
		}

		return extractFromQueryParameters(request.get("Request-Line"));
	}

	private Map<String, String> extractFromBody(BufferedReader br, int contentLength) throws IOException {
		String contents = IOUtils.readData(br, contentLength);
		return parseContents(contents);
	}

	private Map<String, String> extractFromQueryParameters(String requestLine) throws UnsupportedEncodingException {
		String url = extractUrl(requestLine);
		String[] splits = url.split("\\?");
		if (splits.length < 2) {
			return new HashMap<>();
		}

		return parseContents(url.split("\\?")[1]);
	}

	private Map<String, String> parseContents(String contents) throws UnsupportedEncodingException {
		Map<String, String> queryParameters = new HashMap<>();
		contents = URLDecoder.decode(contents, "UTF-8");

		for (String queryParameter : contents.split("&")) {
			String[] splitedQueryParameter = queryParameter.split("=");
			queryParameters.put(splitedQueryParameter[0], splitedQueryParameter[1]);
		}
		return queryParameters;
	}

	public String getFileExtension() {
		String[] splits = url.split("\\.");
		return splits[splits.length - 1];
	}

	public String getMethod() {
		return this.method;
	}

	public String getPath() {
		return extractPath();
	}

	private String extractPath() {
		return this.url.split("\\?")[0];
	}

	public Map<String, String> getHeader() {
		return this.header;
	}

	public Map<String, String> getContents() {
		return this.contents;
	}
}
