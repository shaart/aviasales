package com.epam.aviasales.services;

import com.epam.aviasales.domain.Airport;
import com.epam.aviasales.domain.Ticket;

import java.util.List;

public interface TicketService {

  Ticket getTicket(Long id);
  List<Ticket> getTickets();
  void createTicket(Ticket ticket);
  void deleteTicket(Ticket ticket);
  boolean check(Ticket ticket);

}