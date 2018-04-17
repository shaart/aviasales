package com.epam.aviasales.services.impMock;

import com.epam.aviasales.domain.Airplane;
import com.epam.aviasales.repositories.AirplaneRepository;
import com.epam.aviasales.repositories.implMock.AirplaneRepositoryImplMock;
import com.epam.aviasales.services.AirplaneService;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AirplaneServiceImplMock implements AirplaneService {

  private static volatile AirplaneService instance;

  private final AirplaneRepository airplaneRepository = AirplaneRepositoryImplMock.getInstance();

  public static AirplaneService getInstance() {
    AirplaneService localInstance = instance;
    if (localInstance == null) {
      synchronized (AirplaneServiceImplMock.class) {
        localInstance = instance;
        if (localInstance == null) {
          instance = localInstance = new AirplaneServiceImplMock();
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
  public Airplane getAirplaneById(Long id) {
    return airplaneRepository.getAirplaneById(id);
  }

  @Override
  public void addAirplane(Airplane airplane) {
    airplaneRepository.addAirplane(airplane);

  }

  @Override
  public Airplane getAirplaneByName(String name) {
    return airplaneRepository.getAirplaneByName(name);
  }

  @Override
  public void deleteAirplane(Long id) {
    airplaneRepository.deleteAirplane(id);
  }
}
