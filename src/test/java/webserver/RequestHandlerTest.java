package webserver;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoSettings;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import db.DataBase;
import model.User;

public class RequestHandlerTest {

	// @Test
	// void 파일_경로를_파싱할_수_잇다() {
	// 	Socket socket = new Socket();
	// 	RequestHandler requestHandler = new RequestHandler(socket);
	//
	// 	assertThat(requestHandler.parsePath("GET /index.html HTTP/1.1")).isEqualTo("/index.html");
	// }


	@Test
	void 인덱스_html_GET_요청_테스트() {
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8080/index.html", String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@Test
	void 유저_생성_테스트() {
		RestTemplate restTemplate = new RestTemplate();
		String name = "name";
		String userId = "userId";
		String password = "password";
		String email = "email@email.com";
		User user = new User(userId, password, name, email);

		UriComponentsBuilder builder = UriComponentsBuilder.newInstance()
			.queryParam("name", name)
			.queryParam("userId", userId)
			.queryParam("password", password)
			.queryParam("email", email).host("localhost").path("/user/create").port("8080").scheme("http");


		ResponseEntity<String> response = restTemplate.getForEntity(builder.toUriString(), String.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@Test
	void 쿼리_파라미터_분리_테스트() {
		Map<String, String> expectedQueryParams = new HashMap<>();
		expectedQueryParams.put("userId", "id");
		expectedQueryParams.put("name", "name");
		expectedQueryParams.put("password", "password");
		expectedQueryParams.put("email", "email@email.com");

		String[] requestUrl = new String[2];
		requestUrl[0] = "/user/create";
		requestUrl[1] = "userId=id&name=name&password=password&email=email@email.com";

		Map<String, String> queryParams = HttpRequest.extractQueryParameters(requestUrl);

		assertThat(queryParams).isEqualTo(expectedQueryParams);
	}

	@ParameterizedTest
	@ValueSource(strings = {"html", "css"})
	void 요청에대한_파일타입_반환_테스트(String fileType) {
		HttpRequest httpRequest = new HttpRequest("GET", String.format("file.%s", fileType), new HashMap<String, String>());
		
		assertThat(httpRequest.getFileType()).isEqualTo(fileType);
	}
}
