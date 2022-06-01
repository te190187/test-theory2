package com.example.testtheory2;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class UserTest {

  @Test
  void 正常系_ユーザー管理コード_登録参照(){
    var user = new User("");

    user.setCode("code1");
    assertThat(user.getCode()).isEqualTo("code1");
  }

  @Test
  void 正常系_名前登録参照(){
    var user = new User("");

    user.setName("name1");
    assertThat(user.getName()).isEqualTo("name1");
  }

  @Test
  void 正常系_年齢登録参照(){
    var user = new User("");

    user.setAge(1);
    assertThat(user.getAge()).isEqualTo(1);
  }

  @Test
  void 異常系_範囲外年齢登録(){
    var user = new User("");

    user.setAge(0);
    assertThat(user.getAge()).isEqualTo(-1);
  }

  @Test
  void 異常系_年齢初期値(){
    assertThat(new User("").getAge())
      .isEqualTo(-1);
  }
}
