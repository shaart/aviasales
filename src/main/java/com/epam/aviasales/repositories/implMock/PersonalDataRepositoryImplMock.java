package com.epam.aviasales.repositories.implMock;

import com.epam.aviasales.domain.PersonalData;
import com.epam.aviasales.repositories.PersonalDataRepository;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

public class PersonalDataRepositoryImplMock implements PersonalDataRepository {

  private static volatile PersonalDataRepository instance;
  private static final Map<Long, PersonalData> PERSONAL_DATA_CACHE = new HashMap<>();

  public static PersonalDataRepository getInstance() {
    PersonalDataRepository localInstance = instance;
    if (localInstance == null) {
      synchronized (PersonalDataRepositoryImplMock.class) {
        localInstance = instance;
        if (localInstance == null) {
          instance = localInstance = new PersonalDataRepositoryImplMock();
        }
      }
    }

    return localInstance;
  }

  private PersonalDataRepositoryImplMock() {
    final int CACHE_COUNT = 100;

    LocalDate dateCounter = LocalDate.of(2000, Month.APRIL, 1);
    for (int i = 1; i < CACHE_COUNT; i++) {
      PERSONAL_DATA_CACHE.put(
          Long.valueOf(i),
          PersonalData.builder()
              .id(Long.valueOf(i))
              .name("R-" + i)
              .passport("PASS" + (i * 13))
              .dateOfBirth(dateCounter.plusMonths(i))
              .build());
    }
  }

  @Override
  public List<PersonalData> getPersonalDatas() {
    return getPersonalDatas(1, Integer.MAX_VALUE);
  }

  @Override
  public void updatePersonalDataById(PersonalData personalData){}

  @Override
  public List<PersonalData> getPersonalDatas(int page, int count) {
    List<PersonalData> PersonalDataList = new ArrayList<>();
    if (page <= 0 || count <= 0) {
      return PersonalDataList;
    }

    final int startI = (page - 1) * count;
    for (int i = startI; i < startI + count; i++) {
      if (i >= PERSONAL_DATA_CACHE.size()) {
        break;
      }
      PersonalData personalData = PERSONAL_DATA_CACHE.get(Long.valueOf(i));
      if (personalData != null) {
        PersonalDataList.add(personalData);
      }
    }
    return PersonalDataList;
  }

  @Override
  public PersonalData getPersonalDataByPassport(String passport) {
    for (PersonalData personalData : PERSONAL_DATA_CACHE.values()) {
      if (personalData != null && personalData.getPassport().equals(passport)) {
        return personalData;
      }
    }
    return null;
  }

  @Override
  public PersonalData getPersonalDataById(Long id) {
    return PERSONAL_DATA_CACHE.get(id);
  }

  @Override
  public void addPersonalData(PersonalData personalData) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean isExist(PersonalData personalData) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void updatePersonalData(Long id, PersonalData updatedPersonalData) {
    PERSONAL_DATA_CACHE.put(id, updatedPersonalData);
  }

  @Override
  public void deletePersonalData(Long id) {
    PERSONAL_DATA_CACHE.remove(id);
  }

  @Override
  public List<PersonalData> getPersonalDatasLike(PersonalData seekingPersonalData, int page,
      int size) {

    List<PersonalData> personalDataList = new ArrayList<>();
    if (size <= 0) {
      return personalDataList;
    }
    if (page <= 0) {
      page = 1;
    }

    final int startI = (page - 1) * size;
    for (int i = startI; i < startI + size; i++) {
      if (i >= PERSONAL_DATA_CACHE.size()) {
        break;
      }
      PersonalData personalData = PERSONAL_DATA_CACHE.get(Long.valueOf(i));
      if (personalData != null) {
        if ((seekingPersonalData.getId() == null || seekingPersonalData.getId()
            .equals(personalData.getId()))
            && (seekingPersonalData.getName() == null || seekingPersonalData.getName()
            .equals(personalData.getName()))
            && (seekingPersonalData.getPassport() == null || seekingPersonalData.getPassport()
            .equals(personalData.getPassport()))
            && (seekingPersonalData.getDateOfBirth() == null || seekingPersonalData.getDateOfBirth()
            .equals(personalData.getDateOfBirth()))) {
          personalDataList.add(personalData);
        }
      }
    }
    return personalDataList;
  }
}
