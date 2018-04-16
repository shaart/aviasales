package com.epam.aviasales.services;

import com.epam.aviasales.domain.Airplane;
import com.epam.aviasales.domain.Airport;

import java.util.List;

public interface AirplaneService {

  Airplane getAirplane(Long id);
  List<Airplane> getAirplane();
  void createAirplane(Airplane airplane);
  void deleteAirplane(Airplane airplane);

}