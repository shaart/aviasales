package com.epam.aviasales.services.impl;

import com.epam.aviasales.domain.PersonalData;
import com.epam.aviasales.repositories.PersonalDataRepository;
import com.epam.aviasales.repositories.implMock.PersonalDataRepositoryImplMock;
import com.epam.aviasales.services.PersonalDataService;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

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

  private static final PersonalDataRepository personalDataRepository = PersonalDataRepositoryImplMock
      .getInstance();

  @Override
  public List<PersonalData> getPersonalDatas() {
    return personalDataRepository.getPersonalDatas(1, 20);
  }

  @Override
  public List<PersonalData> getPersonalDatas(int page, int count) {
    return personalDataRepository.getPersonalDatas(page, count);
  }

  @Override
  public PersonalData getPersonalDataByPassport(String name) {
    return personalDataRepository.getPersonalDataByPassport(name);
  }

  @Override
  public PersonalData getPersonalDataById(Long id) {
    return personalDataRepository.getPersonalDataById(id);
  }
}
