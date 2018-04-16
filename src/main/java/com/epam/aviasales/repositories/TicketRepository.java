package com.epam.aviasales.repositories;

import com.epam.aviasales.domain.Ticket;
import java.util.List;

public interface TicketRepository {

  List<Ticket> getTickets();

  List<Ticket> getTickets(int page, int count);

  Ticket getById(Long id);
}
