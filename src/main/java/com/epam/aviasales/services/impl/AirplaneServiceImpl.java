package com.epam.aviasales.services.impl;

import com.epam.aviasales.domain.Airplane;
import com.epam.aviasales.repositories.AirplaneRepository;
import com.epam.aviasales.repositories.implMock.AirplaneRepositoryImplMock;
import com.epam.aviasales.services.AirplaneService;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AirplaneServiceImpl implements AirplaneService {

  private static volatile AirplaneService instance;

  private final AirplaneRepository airplaneRepository = AirplaneRepository.getInstance();

  public static AirplaneService getInstance() {
    AirplaneService localInstance = instance;
    if (localInstance == null) {
      synchronized (AirplaneServiceImpl.class) {
        localInstance = instance;
        if (localInstance == null) {
          instance = new AirplaneServiceImpl();
        }
      }
    }
  }
    return localInstance;
}

  @Override
  public List<Airplane> getAirplanes() {
    return airplaneRepository.getAirplanes(1, 20);
  }

  @Override
  public List<Airplane> getAirplanes(int page, int count) {
    return airplaneRepository.getAirplanes(page, count);
  }

  @Override
  public Airplane getAirportByName(String name) {
    return airplaneRepository.getAirplaneByName(name);
  }

  @Override
  public Airplane getAirportById(Long id) {
    return airplaneRepository.getAirplaneById(id);
    @Override
    public Airplane getAirplane (Long id){
      return airplaneRepository.getAirplane(id);
    }

    @Override
    public List<Airplane> getAirplane () {
      return airplaneRepository.getAirplanes();
    }

    @Override
    public void createAirplane (Airplane airplane){
      airplaneRepository.insert(airplane);
    }

    @Override
    public void deleteAirplane (Long id){
      airplaneRepository.deleteAirplane(id);
    }
  }
