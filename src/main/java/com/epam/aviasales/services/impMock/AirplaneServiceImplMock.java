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

  private final AirplaneRepository airplaneRepositoryMock = AirplaneRepositoryImplMock.getInstance();

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
    return airplaneRepositoryMock.getAirplanes(1, 20);
  }

  @Override
  public List<Airplane> getAirplanes(int page, int count) {
    return airplaneRepositoryMock.getAirplanes(page, count);
  }

  @Override
  public Airplane getAirplaneById(Long id) {
    return airplaneRepositoryMock.getAirplaneById(id);
  }

  @Override
  public void addAirplane(Airplane airplane) {
    airplaneRepositoryMock.addAirplane(airplane);

  }

  @Override
  public Airplane getAirplaneByName(String name) {
    return airplaneRepositoryMock.getAirplaneByName(name);
  }

  @Override
  public void deleteAirplane(Long id) {
    airplaneRepositoryMock.deleteAirplane(id);
  }

  @Override
  public List<Airplane> getAirplanesLike(Airplane seekingAirplane, int page, int size) {
    return airplaneRepositoryMock.getAirplanesLike(seekingAirplane, page, size);
  }

  @Override
  public void updateAirplane(Long id, Airplane updatedAirplane) {
    airplaneRepositoryMock.updateAirplane(id, updatedAirplane);
  }
}
