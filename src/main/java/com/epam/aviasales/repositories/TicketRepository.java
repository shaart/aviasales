package com.epam.aviasales.repositories;

import com.epam.aviasales.domain.Ticket;
import java.util.List;

public interface TicketRepository {

  List<Ticket> getTickets();

  List<Ticket> getTickets(int page, int count);

  Ticket getTicketById(Long id);

  void addTicket(Ticket ticket);

  void deleteTicket(Long id);

  boolean isExist(Long id);
}
