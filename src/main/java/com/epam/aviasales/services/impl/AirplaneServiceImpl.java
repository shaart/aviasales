package com.epam.aviasales.services.impl;

import com.epam.aviasales.domain.Airplane;
import com.epam.aviasales.repositories.AirplaneRepository;
import com.epam.aviasales.services.AirplaneService;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AirplaneServiceImpl implements AirplaneService{

  private static final AirplaneService instance = new AirplaneServiceImpl();

  private final AirplaneRepository airplaneRepository = AirplaneRepository.getInstance();

  public static AirplaneService getInstance() {
    return instance;
  }

  @Override
  public Airplane getAirplane(Long id) {
    return airplaneRepository.getAirplane(id);
  }

  @Override
  public List<Airplane> getAirplane() {
    return airplaneRepository.getAirplanes();
  }

  @Override
  public void createAirplane(Airplane airplane) {
    airplaneRepository.insert(airplane);
  }

  @Override
  public void deleteAirplane(Airplane airplane) {
    airplaneRepository.delete(airplane);
  }
}
