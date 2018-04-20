package com.epam.aviasales.services;

import com.epam.aviasales.domain.Account;
import com.epam.aviasales.domain.Airplane;
import com.epam.aviasales.domain.Airport;
import com.epam.aviasales.domain.Flight;
import com.epam.aviasales.domain.PersonalData;
import com.epam.aviasales.domain.Ticket;
import com.epam.aviasales.util.CastType;
import javax.servlet.http.HttpServletRequest;

public interface ParserService {

  /**
   * Parses string <code>parameter</code> to selected <code>CastType</code>.<br> Notes:<br> Flight
   * parsing by ID,<br> Account - by login,<br> PersonalData - by passport<br>Airport & Airplane -
   * by name<br>
   *
   * @param parameter Parsing value
   * @param type Out type
   * @return parsed object or null
   */
  <T> T parseParameter(String parameter, CastType type);

  Flight parseFlight(HttpServletRequest req);

  Airport parseAirport(HttpServletRequest req);

  Airplane parseAirplane(HttpServletRequest req);

  PersonalData parsePersonalData(HttpServletRequest req);

  Ticket parseTicket(HttpServletRequest req);

  Account parseAccount(HttpServletRequest req);
}
