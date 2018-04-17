package com.epam.aviasales.services;

import com.epam.aviasales.domain.Ticket;
import java.util.List;

public interface ProfileService {
  List<Ticket> getAccountTickets(Long accountId);
  void deleteAccountTicketById(Long ticketId);

}
