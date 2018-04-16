package com.epam.aviasales.repositories;

import com.epam.aviasales.domain.PersonalData;
import java.util.List;

public interface PersonalDataRepository {

  List<PersonalData> getPersonalDatas();

  List<PersonalData> getPersonalDatas(int page, int count);

  PersonalData getByPassport(String passport);

  PersonalData getById(Long id);
}
