package com.epam.aviasales.repositories.implMock;

import com.epam.aviasales.domain.Airport;
import com.epam.aviasales.repositories.AirportRepository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AirportRepositoryImplMock implements AirportRepository {

  private static volatile AirportRepository instance;

  public static AirportRepository getInstance() {
    AirportRepository localInstance = instance;
    if (localInstance == null) {
      synchronized (AirportRepositoryImplMock.class) {
        localInstance = instance;
        if (localInstance == null) {
          instance = localInstance = new AirportRepositoryImplMock();
        }
      }
    }

    return localInstance;
  }

  private static final Map<Long, Airport> AIRPORT_CACHE = new HashMap<>();

  static {
    final int CACHE_COUNT = 100;

    AIRPORT_CACHE.put(1L, new Airport(1L, "Moscow"));
    for (int i = 2; i < CACHE_COUNT; i++) {
      AIRPORT_CACHE.put(Long.valueOf(i),
          Airport.builder().id(Long.valueOf(i)).name("Saint-Petersburg-" + i).build());
    }
  }

  @Override
  public List<Airport> getAirports() {
    return getAirports(1, Integer.MAX_VALUE);
  }

  @Override
  public List<Airport> getAirports(int page, int count) {
    List<Airport> airportList = new ArrayList<>();
    if (page <= 0 || count <= 0) {
      return airportList;
    }

    final int startI = (page - 1) * count;
    for (int i = startI; i < startI + count; i++) {
      if (i >= AIRPORT_CACHE.size()) {
        break;
      }
      airportList.add(AIRPORT_CACHE.get(Long.valueOf(i)));
    }
    return airportList;
  }

  @Override
  public Airport getAirportByName(String name) {
    for (Airport airport : AIRPORT_CACHE.values()) {
      if (airport.getName().equals(name)) {
        return airport;
      }
    }
    return null;
  }

  @Override
  public Airport getAirportById(Long id) {
    return AIRPORT_CACHE.get(id);
  }
}
