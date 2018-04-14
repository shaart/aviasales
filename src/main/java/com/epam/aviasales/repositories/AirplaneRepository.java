package com.epam.aviasales.repositories;

import com.epam.aviasales.domain.Airplane;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AirplaneRepository {

  private static final AirplaneRepository instance = new AirplaneRepository();

  public static AirplaneRepository getInstance() {
    return instance;
  }

  private static final Map<Long, Airplane> AIRPLANE_CACHE = new HashMap<>();

  static {
    final int CACHE_COUNT = 100;

    AIRPLANE_CACHE.put(1L, new Airplane(1L, "Boeing 747", 5, 5));
    for (int i = 2; i < CACHE_COUNT; i++) {
      AIRPLANE_CACHE.put(Long.valueOf(i), new Airplane(Long.valueOf(i), "SU-" + i, 15, 20));
    }
  }

  public List<Airplane> getAirplanes() {
    return getAirplanes(1, Integer.MAX_VALUE);
  }

  public List<Airplane> getAirplanes(int page, int count) {
    List<Airplane> airplaneList = new ArrayList<>();
    if (page <= 0 || count <= 0) {
      return airplaneList;
    }

    final int startI = (page - 1) * count;
    for (int i = startI; i < startI + count; i++) {
      if (i >= AIRPLANE_CACHE.size()) {
        break;
      }
      airplaneList.add(AIRPLANE_CACHE.get(Long.valueOf(i)));
    }
    return airplaneList;
  }

  public Airplane getByName(String name) {
    for (Airplane airplane : AIRPLANE_CACHE.values()) {
      if (airplane.getName().equals(name)) {
        return airplane;
      }
    }
    return null;
  }

  public Airplane getById(Long id) {
    return AIRPLANE_CACHE.get(id);
  }
}
