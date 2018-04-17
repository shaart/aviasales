package com.epam.aviasales.services;

import com.epam.aviasales.domain.Airplane;
import java.util.List;

public interface AirplaneService {

  List<Airplane> getAirplanes();

  List<Airplane> getAirplanes(int page, int count);

  Airplane getAirplaneByName(String name);

  Airplane getAirplaneById(Long id);

  void addAirplane(Airplane airplane);

  void deleteAirplane(Long id);
}