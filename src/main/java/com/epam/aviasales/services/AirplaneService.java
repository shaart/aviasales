package com.epam.aviasales.services;

import com.epam.aviasales.domain.Airplane;
import java.util.List;

public interface AirplaneService {

  List<Airplane> getAirplanes();

  List<Airplane> getAirplanes(int page, int count);

  Airplane getByName(String name);

  Airplane getById(Long id);
}
