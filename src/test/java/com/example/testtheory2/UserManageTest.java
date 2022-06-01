package com.example.testtheory2;

import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserManageTest {
  static UserManager manager = UserManager.getInstance();

  @AfterEach
  void tearDown(){
    // シングルトンのテストを行っているため、テスト間でオブジェクトを共有してしまう。
    // シングルトンをクリアするようなメソッドがないため、とりあえず各テスト後に
    // ユーザーを全部削除する。
    manager.deleteAllUser();
  }

  @Test
  void 正常系_UserManagerインスタンス同一(){
    var manager1 = UserManager.getInstance();
    var manager2 = UserManager.getInstance();

    assertThat(manager1).isEqualTo(manager2);
  }

  @Test
  void 正常系_userList登録参照(){
    var user1 = new User("user1");
    var user2 = new User("user2");

    manager.setUserToList(user1);
    manager.setUserToList(user2);

    assertThat(manager.getUserList()).containsOnly(user1, user2);
  }

  @Test
  void 正常系_userMap登録参照(){
    var user1 = new User("user1");
    var user2 = new User("user2");

    manager.setUserToMap(user1);
    manager.setUserToMap(user2);
    
    
    assertThat(manager.getUserMap())
      .containsValues(user1, user2);
  }

  @Test
  void 正常系_user全削除(){
    var user1 = new User("user1");
    var user2 = new User("user2");

    manager.setUserToList(user1);
    manager.setUserToList(user2);
    manager.setUserToMap(user1);
    manager.setUserToMap(user2);

    manager.deleteAllUser();

    assertThat(manager.getUserList())
      .doesNotContain(user1, user2);
      
    assertThat(manager.getUserMap())
      .doesNotContainEntry(user1.getCode(), user1)
      .doesNotContainEntry(user2.getCode(), user2);
  }

  @Test
  void 正常系_code指定user削除(){
    var user1 = new User("user1");
    var user2 = new User("user2");

    manager.setUserToList(user1);
    manager.setUserToList(user2);
    manager.setUserToMap(user1);
    manager.setUserToMap(user2);

    manager.deleteUser(user1.getCode());

    assertThat(manager.getUserList())
      .doesNotContain(user1)
      .contains(user2);

    assertThat(manager.getUserMap())
      .doesNotContainEntry(user1.getCode(), user1)
      .containsEntry(user2.getCode(), user2);
  }

  @Test
  void 異常系_同一code指定uesr削除(){
    var userCode = "same";
    var user1 = new User(userCode);
    var user2 = new User(userCode);

    manager.setUserToList(user1);
    manager.setUserToList(user2);

    manager.deleteUser(userCode);

    assertThat(manager.getUserList())
      .doesNotContain(user1)
      .contains(user2);
  }

  @Test
  void 正常系_MapList初期生成(){
    assertThat(manager.getUserList())
      .isNotNull()
      .isEmpty();
    
    assertThat(manager.getUserMap())
      .isNotNull()
      .isEmpty();
  } 

  @Test
  void 正常系_List登録順序保持(){
    var user1 = new User("user1");
    var user2 = new User("user2");
    var user3 = new User("user3");

    manager.setUserToList(user3);
    manager.setUserToList(user1);
    manager.setUserToList(user2);

    assertThat(manager.getUserList())
      .containsSequence(user3,user1,user2);
  }

  @Test
  void 正常系_Mapキー確認(){
    var user1 = new User("user1");
    var user2 = new User("user2");
    
    manager.setUserToMap(user1);
    manager.setUserToMap(user2);

    assertThat(manager.getUserMap())
      .containsKeys(user1.getCode(), user2.getCode());
  }
}
