package com.epam.aviasales.services;

import com.epam.aviasales.domain.Airport;
import com.epam.aviasales.domain.Flight;
import com.epam.aviasales.util.CastType;
import javax.servlet.http.HttpServletRequest;

public interface ParserService {

  /**
   * Parses string <code>parameter</code> to selected <code>CastType</code>.<br>
   *
   * @param parameter Parsing value
   * @param type Out type
   * @return parsed object or null
   */
  <T> T parseParameter(String parameter, CastType type);

  Flight parseFlight(HttpServletRequest req);

  Airport parseAirport(HttpServletRequest req);
}
