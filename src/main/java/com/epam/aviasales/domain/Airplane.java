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
public class Airplane implements Serializable {

  private Long id;
  private String name;
  private Integer businessSeatsCount;
  private Integer economySeatsCount;
}
