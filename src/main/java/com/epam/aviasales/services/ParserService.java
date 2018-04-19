package com.epam.aviasales.services;

import com.epam.aviasales.domain.Flight;
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

  Flight parseFlight(HttpServletRequest req);
}
