package com.epam.aviasales.domain;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Account implements Serializable {

  private Long id;
  private Role role;
  private String name;
  private String login;
  private String password;
  private String email;
  private String phone;

  @Override
  public String toString() {
    return String
        .format("Account(id=%s, role=%s, name=%s, login=%s, email=%s, phone=%s",
            id, role, name, login, email, phone);
  }
}
