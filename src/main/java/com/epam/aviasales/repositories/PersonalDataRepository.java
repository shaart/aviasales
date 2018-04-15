package com.epam.aviasales.repositories;

import com.epam.aviasales.domain.PersonalData;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PersonalDataRepository {

  private static final PersonalDataRepository instance = new PersonalDataRepository();

  public static PersonalDataRepository getInstance() {
    return instance;
  }

  private static final Map<Long, PersonalData> PERSONAL_DATA_CACHE = new HashMap<>();

  static {
    final int CACHE_COUNT = 100;

    LocalDate dateCounter = LocalDate.of(2000, Month.APRIL, 1);
    for (int i = 1; i < CACHE_COUNT; i++) {
      PERSONAL_DATA_CACHE.put(Long.valueOf(i),
          new PersonalData(Long.valueOf(i), "R-" + i, "PASS-" + (i * 13),
              dateCounter.plusMonths(i)));
    }
  }

  public List<PersonalData> getPersonalDatas() {
    return getPersonalDatas(1, Integer.MAX_VALUE);
  }

  public List<PersonalData> getPersonalDatas(int page, int count) {
    List<PersonalData> PersonalDataList = new ArrayList<>();
    if (page <= 0 || count <= 0) {
      return PersonalDataList;
    }

    final int startI = (page - 1) * count + 1;
    final int REQ_NUM = count == Integer.MAX_VALUE ? count : startI + count;
    for (int i = startI; i < REQ_NUM; i++) {
      if (i >= PERSONAL_DATA_CACHE.size()) {
        break;
      }
      PersonalDataList.add(PERSONAL_DATA_CACHE.get(Long.valueOf(i)));
    }
    return PersonalDataList;
  }

  public PersonalData getByPassport(String passport) {
    for (PersonalData PersonalData : PERSONAL_DATA_CACHE.values()) {
      if (PersonalData.getPassport().equals(passport)) {
        return PersonalData;
      }
    }
    return null;
  }

  public PersonalData getById(Long id) {
    return PERSONAL_DATA_CACHE.get(id);
  }
}
