package com.epam.aviasales.domain;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Airplane implements Serializable {

  private Long id;
  private String name;
  private Integer businessSeatsCount;
  private Integer economySeatsCount;
}
