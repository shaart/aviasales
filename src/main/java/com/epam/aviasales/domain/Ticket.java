package com.epam.aviasales.domain;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Ticket implements Serializable {

  private Long id;
  private PersonalData personalData;
  private Flight flight;
  private Account account;
  private Integer price;
  private Boolean isBusiness;
}
