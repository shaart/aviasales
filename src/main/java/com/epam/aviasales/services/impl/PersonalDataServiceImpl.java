package com.epam.aviasales.services.impl;

import com.epam.aviasales.domain.PersonalData;
import com.epam.aviasales.exceptions.PersonalDataAlreadyExists;
import com.epam.aviasales.repositories.PersonalDataRepository;
import com.epam.aviasales.repositories.impl.PersonalDataRepositoryImpl;
import com.epam.aviasales.services.PersonalDataService;

import java.util.List;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j;

@Log4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PersonalDataServiceImpl implements PersonalDataService {

  private static volatile PersonalDataService instance;

  public static PersonalDataService getInstance() {
    PersonalDataService localInstance = instance;
    if (localInstance == null) {
      synchronized (PersonalDataServiceImpl.class) {
        localInstance = instance;
        if (localInstance == null) {
          instance = localInstance = new PersonalDataServiceImpl();
        }
      }
    }

    return localInstance;
  }

  private static final PersonalDataRepository personalDataRepository = PersonalDataRepositoryImpl
      .getInstance();

  @Override
  public List<PersonalData> getPersonalDatas() {
    return personalDataRepository.getPersonalDatas();
  }

  @Override
  public List<PersonalData> getPersonalDatas(int page, int count) {
    return personalDataRepository.getPersonalDatas(page, count);
  }

  @Override
  public PersonalData getPersonalDataByPassport(String passport) {
    return personalDataRepository.getPersonalDataByPassport(passport);
  }

  @Override
  public PersonalData getPersonalDataById(Long id) {
    return personalDataRepository.getPersonalDataById(id);
  }

  @Override
  public void addPersonalData(PersonalData personalData) throws PersonalDataAlreadyExists {
    PersonalData personalDataInsideDB = personalDataRepository
        .getPersonalDataByPassport(personalData.getPassport());
    if (personalDataInsideDB == null) {
      personalDataRepository.addPersonalData(personalData);
      log.info("Added Personal Data: " + personalData);
    } else {
      if (!personalDataInsideDB.getName().equals(personalData.getName()) ||
          !personalDataInsideDB.getDateOfBirth().equals(personalData.getDateOfBirth())) {
        throw new PersonalDataAlreadyExists();
      }
    }
  }

  @Override
  public boolean isExist(PersonalData personalData) {
    return personalDataRepository.isExist(personalData);
  }

  @Override
  public void updatePersonalData(Long id, PersonalData updatedPersonalData) {
    personalDataRepository.updatePersonalData(id, updatedPersonalData);
  }

  @Override
  public void deletePersonalData(Long id) {
    personalDataRepository.deletePersonalData(id);
  }

  @Override
  public List<PersonalData> getPersonalDatasLike(PersonalData seekingPersonalData, int page,
      int size) {
    return personalDataRepository.getPersonalDatasLike(seekingPersonalData, page, size);
  }
}
