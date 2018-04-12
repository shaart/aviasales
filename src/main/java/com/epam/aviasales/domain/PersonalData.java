package com.epam.aviasales.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
public class PersonalData {
    private Long id;
    private String name;
    private String passport;
    private LocalDate dateOfBirth;
}