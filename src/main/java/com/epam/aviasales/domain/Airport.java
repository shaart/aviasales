package com.epam.aviasales.domain;

import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Airport implements Serializable {

  private Long id;
  private String name;
}
