package webserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import utils.FileIoUtils;
import utils.IOUtils;

public class HttpRequest {

	private final String method;
	private final String path;
	private final Map<String, String> queryParameters;
	private final Map<String, String> header;

	public HttpRequest(String method, String path, Map<String, String> queryParameters, Map<String, String> header) {
		this.method = method;
		this.path = path;
		this.queryParameters = queryParameters;
		this.header = header;
	}

	public static HttpRequest from(BufferedReader br) throws IOException {
		Map<String, String> header = getRequestHeader(br);

		String method = header.get("RequestLine").split(" ")[0];

		String[] requestUrl = header.get("RequestLine").split(" ")[1].split("\\?");

		if (method.equals("GET")) {

			if (requestUrl.length > 1) {
				Map<String, String> queryParameters = extractQueryParameters(requestUrl[1]);

				return new HttpRequest(method, requestUrl[0], queryParameters, header);
			}
		}

		if (method.equals("POST")) {
			String parameters = IOUtils.readData(br, Integer.parseInt(header.get("Content-Length")));
			Map<String, String> queryParameters = extractQueryParameters(parameters);
			return new HttpRequest(method, requestUrl[0], queryParameters, header);
		}

		return new HttpRequest(method, requestUrl[0], new HashMap<>(), header);
	}

	public static Map<String, String> extractQueryParameters(String parameters) throws UnsupportedEncodingException {
		Map<String, String> queryParameters = new HashMap<>();
		parameters = URLDecoder.decode(parameters, "UTF-8");

		for (String queryParameter : parameters.split("&")) {
			String[] splitedQueryParameter = queryParameter.split("=");
			queryParameters.put(splitedQueryParameter[0], splitedQueryParameter[1]);
		}
		return queryParameters;
	}

	private static Map<String, String> getRequestHeader(BufferedReader br) throws IOException {
		Map<String, String> header = new HashMap<String, String>();

		String line = br.readLine();
		header.put("RequestLine", line);

		line = br.readLine();
		while (!"".equals(line) && line != null) {
			String[] splitedLine = line.split(": ");
			header.put(splitedLine[0], splitedLine[1]);
			line = br.readLine();
		}

		return header;
	}

	public String getFileType() {
		return path.split("\\.")[path.split("\\.").length - 1];
	}

	public String getPath() {
		if (getFileType().equals("css")) {
			return "./static" + path;
		}
		if (getFileType().equals("html") || getFileType().equals("ico")) {
			return "./templates" + path;
		}
		return path;
	}

	public byte[] getBody() throws IOException, URISyntaxException {
		if (isFile()) {
			return FileIoUtils.loadFileFromClasspath(getPath());
 		}

		return new byte[0];
	}

	public String getContentType() {
		if (getFileType().equals("html")) {
			return "text/html;charset=utf-8";
		}

		return "text/css";
	}

	public Map<String, String> getQueryParameters() {
		return queryParameters;
	}

	public String getMethod() {
		return this.method;
	}

	public boolean isFile() {
		String fileType = getFileType();
		return (fileType.equals("html") || fileType.equals("css") || fileType.equals("ico"));
	}
}
