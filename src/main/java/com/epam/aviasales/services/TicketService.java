package com.epam.aviasales.services;

import com.epam.aviasales.domain.Ticket;
import com.epam.aviasales.exceptions.NoAvailableSeatsForTheFlight;

import java.util.List;

public interface TicketService {

  List<Ticket> getTickets();

  List<Ticket> getTickets(int page, int count);

  Ticket getTicketById(Long id);

  void addTicket(Ticket ticket) throws NoAvailableSeatsForTheFlight;

  void deleteTicket(Long id);

  boolean isValid(Ticket ticket);

  List<Ticket> getTicketsLike(Ticket seekingTicket, int page, int size);

  void updateTicket(Long id, Ticket receivedTicket);
}