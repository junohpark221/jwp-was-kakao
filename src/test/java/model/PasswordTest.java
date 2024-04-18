package model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PasswordTest {
    @Test
    void 생성자_테스트() {
        Password password = new Password("password");

        assertThat(password).isNotNull();
    }

    @Test
    void 비밀번호_검증_테스트() {
        Password password = new Password("1q2w3e4r!");

        assertThat(password.isCorrectPassword("1q2w3e4r!")).isTrue();
    }
}
