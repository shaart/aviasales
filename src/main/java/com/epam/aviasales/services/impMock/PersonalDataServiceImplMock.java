package com.epam.aviasales.services.impMock;

import com.epam.aviasales.domain.PersonalData;
import com.epam.aviasales.repositories.PersonalDataRepository;
import com.epam.aviasales.repositories.implMock.PersonalDataRepositoryImplMock;
import com.epam.aviasales.services.PersonalDataService;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PersonalDataServiceImplMock implements PersonalDataService {

  private static volatile PersonalDataService instance;

  public static PersonalDataService getInstance() {
    PersonalDataService localInstance = instance;
    if (localInstance == null) {
      synchronized (PersonalDataServiceImplMock.class) {
        localInstance = instance;
        if (localInstance == null) {
          instance = localInstance = new PersonalDataServiceImplMock();
        }
      }
    }

    return localInstance;
  }

  private static final PersonalDataRepository personalDataRepositoryMock = PersonalDataRepositoryImplMock
      .getInstance();

  @Override
  public List<PersonalData> getPersonalDatas() {
    return personalDataRepositoryMock.getPersonalDatas(1, 20);
  }

  @Override
  public List<PersonalData> getPersonalDatas(int page, int count) {
    return personalDataRepositoryMock.getPersonalDatas(page, count);
  }

  @Override
  public PersonalData getPersonalDataByPassport(String name) {
    return personalDataRepositoryMock.getPersonalDataByPassport(name);
  }

  @Override
  public PersonalData getPersonalDataById(Long id) {
    return personalDataRepositoryMock.getPersonalDataById(id);
  }

  @Override
  public void addPersonalData(PersonalData personalData) {
    personalDataRepositoryMock.addPersonalData(personalData);
  }

  @Override
  public boolean isExist(PersonalData personalData) {
    return personalDataRepositoryMock.isExist(personalData);
  }

  @Override
  public void updatePersonalData(Long id, PersonalData updatedPersonalData) {
    personalDataRepositoryMock.updatePersonalData(id, updatedPersonalData);
  }

  @Override
  public void deletePersonalData(Long id) {
    personalDataRepositoryMock.deletePersonalData(id);
  }

  @Override
  public List<PersonalData> getPersonalDatasLike(PersonalData seekingPersonalData, int page,
      int size) {
    return personalDataRepositoryMock.getPersonalDatasLike(seekingPersonalData, page, size);
  }
}
