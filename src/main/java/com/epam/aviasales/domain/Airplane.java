package com.epam.aviasales.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class Airplane {

  private Integer id;
  private String name;
  private Integer businessSeatsNumber;
  private Integer economySeatsNumber;
}
