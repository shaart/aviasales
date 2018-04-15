package com.epam.aviasales.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Account {
    private Long id;
    private Integer type;
    private String name;
    private String login;
    private String password;
    private String email;
    private String phone;
}
