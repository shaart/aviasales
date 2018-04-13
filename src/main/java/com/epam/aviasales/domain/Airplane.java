package com.epam.aviasales.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class Airplane {

  private Long id;
  private String name;
  private Integer businessSeatsCount;
  private Integer economySeatsCount;
}
