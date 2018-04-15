package com.epam.aviasales.repositories;

import com.epam.aviasales.domain.Airport;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AirportRepository {

  private static final AirportRepository instance = new AirportRepository();

  public static AirportRepository getInstance() {
    return instance;
  }

  private static final Map<Long, Airport> AIRPORT_CACHE = new HashMap<>();

  static {
    final int CACHE_COUNT = 100;

    AIRPORT_CACHE.put(1L, new Airport(1L, "Moscow"));
    for (int i = 2; i < CACHE_COUNT; i++) {
      AIRPORT_CACHE.put(Long.valueOf(i), new Airport(Long.valueOf(i), "Saint-Petersburg-" + i));
    }
  }

  public List<Airport> getAirports() {
    return getAirports(1, Integer.MAX_VALUE);
  }

  public List<Airport> getAirports(int page, int count) {
    List<Airport> airportList = new ArrayList<>();
    if (page <= 0 || count <= 0) {
      return airportList;
    }

    final int startI = (page - 1) * count + 1;
    final int REQ_NUM = count == Integer.MAX_VALUE ? count : startI + count;
    for (int i = startI; i < REQ_NUM; i++) {
      if (i >= AIRPORT_CACHE.size()) {
        break;
      }
      airportList.add(AIRPORT_CACHE.get(Long.valueOf(i)));
    }
    return airportList;
  }

  public Airport getByName(String name) {
    for (Airport airport : AIRPORT_CACHE.values()) {
      if (airport.getName().equals(name)) {
        return airport;
      }
    }
    return null;
  }

  public Airport getById(Long id) {
    return AIRPORT_CACHE.get(id);
  }
}
