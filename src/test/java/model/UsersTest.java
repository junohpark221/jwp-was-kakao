package model;

import db.DataBase;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class UsersTest {
    @Test
    void 생성자_테스트() {
        User user01 = new User("userId01", "password01", "name01", "email01");
        User user02 = new User("userId02", "password02", "name02", "email02");

        DataBase.addUser(user01);
        DataBase.addUser(user02);

        Users users = new Users();
        assertThat(users.getValues().getOrDefault("users", null)).isNotNull();
    }
}
