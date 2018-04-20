package com.epam.aviasales.repositories.implMock;

import com.epam.aviasales.domain.Airplane;
import com.epam.aviasales.repositories.AirplaneRepository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

public class AirplaneRepositoryImplMock implements AirplaneRepository {

  private static volatile AirplaneRepository instance;
  private static final Map<Long, Airplane> AIRPLANE_CACHE = new HashMap<>();

  public static AirplaneRepository getInstance() {
    AirplaneRepository localInstance = instance;
    if (localInstance == null) {
      synchronized (AirplaneRepositoryImplMock.class) {
        localInstance = instance;
        if (localInstance == null) {
          instance = localInstance = new AirplaneRepositoryImplMock();
        }
      }
    }

    return localInstance;
  }

  private AirplaneRepositoryImplMock() {
    final int CACHE_COUNT = 100;

    AIRPLANE_CACHE.put(1L, new Airplane(1L, "Boeing 747", 5, 5));
    for (int i = 2; i < CACHE_COUNT; i++) {
      AIRPLANE_CACHE.put(Long.valueOf(i),
          Airplane.builder().id(Long.valueOf(i)).name("SU-" + i).economySeatsCount(15)
              .businessSeatsCount(20).build());
    }
  }

  @Override
  public List<Airplane> getAirplanes() {
    return getAirplanes(1, Integer.MAX_VALUE);
  }

  @Override
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
      Airplane airplane = AIRPLANE_CACHE.get(Long.valueOf(i));
      if (airplane != null) {
        airplaneList.add(airplane);
      }
    }
    return airplaneList;
  }

  @Override
  public Airplane getAirplaneByName(String name) {
    for (Airplane airplane : AIRPLANE_CACHE.values()) {
      if (airplane.getName().equals(name)) {
        return airplane;
      }
    }
    return null;
  }

  @Override
  public Airplane getAirplaneById(Long id) {
    return AIRPLANE_CACHE.get(id);
  }

  @Override
  public void deleteAirplane(Long id) {
    AIRPLANE_CACHE.remove(id);
  }

  @Override
  public void addAirplane(Airplane airplane) {
    AIRPLANE_CACHE.put(airplane.getId(), airplane);
  }

  @Override
  public boolean isExist(Long id) {
    return AIRPLANE_CACHE.containsKey(id);
  }

  @Override
  public List<Airplane> getAirplanesLike(Airplane seekingAirplane, int page, int size) {

    List<Airplane> airplaneList = new ArrayList<>();
    if (size <= 0) {
      return airplaneList;
    }
    if (page <= 0) {
      page = 1;
    }

    final int startI = (page - 1) * size;
    for (int i = startI; i < startI + size; i++) {
      if (i >= AIRPLANE_CACHE.size()) {
        break;
      }
      Airplane flight = AIRPLANE_CACHE.get(Long.valueOf(i));
      if (flight != null) {
        if ((seekingAirplane.getId() == null || seekingAirplane.getId().equals(flight.getId()))
            && (seekingAirplane.getName() == null || seekingAirplane.getName()
            .equals(flight.getName()))) {
          airplaneList.add(flight);
        }
      }
    }
    return airplaneList;
  }

  @Override
  public void updateAirplane(Long id, Airplane updatedAirplane) {
    AIRPLANE_CACHE.put(id, updatedAirplane);
  }
}
