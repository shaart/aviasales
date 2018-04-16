package com.epam.aviasales.services.impl;

import com.epam.aviasales.domain.Flight;
import com.epam.aviasales.repositories.FlightRepository;
import com.epam.aviasales.services.FlightsService;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FlightsServiceImpl implements FlightsService {

    private static final FlightsService instance = new FlightsServiceImpl();

    private final FlightRepository flightRepository = FlightRepository.getInstance();

    public static FlightsService getInstance() {
        return instance;
    }

    @Override
    public void createFlight(Flight flight) {
        flightRepository.insert(flight);
    }

    @Override
    public void deleteFlight(Long id) {
        flightRepository.delete(id);
    }

    @Override
    public Flight getFlight(Long id){
        return flightRepository.getFlight(id);
    }

    @Override
    public List<Flight> getFlights(Long from, Long to) {
        return flightRepository.getFlights(from,to);
    }

    @Override
    public List<Flight> getFlights() {
        return flightRepository.getFlights();
    }
}
