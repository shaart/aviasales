package com.epam.aviasales.domain;

import lombok.AllArgsConstructor;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PersonalData implements Serializable {

  private Long id;
  private String name;
  private String passport;
  private LocalDate dateOfBirth;
}