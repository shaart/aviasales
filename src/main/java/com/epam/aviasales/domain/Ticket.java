package com.epam.aviasales.domain;

import lombok.AllArgsConstructor;
import java.io.Serializable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Ticket implements Serializable {

  private Long id;
  private PersonalData personalData;
  private Flight flight;
  private Account account;
  private Integer price;
  private Boolean isBusiness;
}
