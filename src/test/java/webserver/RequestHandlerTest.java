package webserver;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import db.DataBase;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import model.User;

public class RequestHandlerTest {
	private static Thread server;

	@BeforeAll
    static void runServer() {
		server = new Thread(() ->
        {
            try {
                WebApplicationServer.main(new String[0]);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
		);
		server.start();
	}

	@AfterAll
    static void stopServer() {
		server.interrupt();
	}

	@Test
	void 인덱스_html_GET_요청_테스트() {
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:8080/index.html", String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@Test
	void POST_유저_생성_테스트() {
		RestTemplate restTemplate = new RestTemplate();
		String name = "name";
		String userId = "userId";
		String password = "password";
		String email = "email@email.com";
		User user = new User(userId, password, name, email);

		MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
		parameters.add("name", name);
		parameters.add("userId", userId);
		parameters.add("password", password);
		parameters.add("email", email);

		ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:8080/user/create", parameters, String.class);

		assertAll(
				() -> assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND),
				() -> assertThat(DataBase.findUserById(userId)).isEqualTo(user)
		);
	}
}
