package com.epam.aviasales.services;

import com.epam.aviasales.domain.Airport;
import com.epam.aviasales.domain.Flight;
import com.epam.aviasales.util.CastType;
import javax.servlet.http.HttpServletRequest;

public interface ParserService {

  /**
   * Parses string <code>parameter</code> to <code>type</code>.<br> Types: <br> - Long, Integer;
   * <br> - Airport, Airplane (get by name using services)<br> - LocalDateTime
   *
   * @param parameter Parsing value
   * @param type Out type
   * @return Parsed parameter
   */
  Object parseParameter(String parameter, Class type);

  <T> T parseParameter(String parameter, CastType type);

  Flight parseFlight(HttpServletRequest req);

  Airport parseAirport(HttpServletRequest req);
}
