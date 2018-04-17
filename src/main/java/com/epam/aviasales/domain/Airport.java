package com.epam.aviasales.domain;

import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Airport implements Serializable {

  private Long id;
  private String name;
}
