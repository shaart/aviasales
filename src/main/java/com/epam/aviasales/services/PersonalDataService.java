package com.epam.aviasales.services;

import com.epam.aviasales.domain.PersonalData;
import java.util.List;

public interface PersonalDataService {

  List<PersonalData> getPersonalDatas();

  List<PersonalData> getPersonalDatas(int page, int count);

  PersonalData getPersonalDataByPassport(String passport);

  PersonalData getPersonalDataById(Long id);

  void addPersonalData(PersonalData personalData);

  boolean isExist(PersonalData personalData);

  void updatePersonalData(Long id, PersonalData updatedPersonalData);

  void deletePersonalData(Long id);

  List<PersonalData> getPersonalDatasLike(PersonalData seekingPersonalData, int page, int size);
}
