package com.epam.aviasales.repositories;

import com.epam.aviasales.domain.Airplane;
import java.util.List;

public interface AirplaneRepository {

  List<Airplane> getAirplanes();

  List<Airplane> getAirplanes(int page, int count);

  Airplane getAirplaneByName(String name);

  Airplane getAirplaneById(Long id);
}
