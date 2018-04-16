package com.epam.aviasales.repositories;

import com.epam.aviasales.domain.Airplane;
import java.util.List;

public interface AirplaneRepository {

  List<Airplane> getAirplanes();

  List<Airplane> getAirplanes(int page, int count);

  Airplane getByName(String name);

  Airplane getById(Long id);
}
