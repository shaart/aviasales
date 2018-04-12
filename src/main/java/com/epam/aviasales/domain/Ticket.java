package com.epam.aviasales.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class Ticket {

  private Long id;
  private PersonalData personalData;
  private Flight flight;
  private Account account;
  private Integer price;
  private Boolean isBusiness;
}
