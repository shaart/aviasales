package com.epam.aviasales.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
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
