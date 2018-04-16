package com.epam.aviasales.services;

import com.epam.aviasales.domain.Ticket;
import java.util.List;

public interface TicketService {

  List<Ticket> getTickets();

  List<Ticket> getTickets(int page, int count);

  Ticket getTicketById(Long id);

  void addTicket(Ticket ticket);

  Ticket getTicket(Long id);

  void deleteTicket(Long id);

  boolean isValid(Ticket ticket);
}