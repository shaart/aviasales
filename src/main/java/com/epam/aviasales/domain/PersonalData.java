package com.epam.aviasales.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
public class PersonalData {
    private int id;
    private String name;
    private String passport;
    private LocalDate dateOfBirth;
}