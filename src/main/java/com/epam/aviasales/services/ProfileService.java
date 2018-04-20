package com.epam.aviasales.services;

import com.epam.aviasales.domain.Account;
import com.epam.aviasales.domain.PersonalData;
import com.epam.aviasales.domain.Ticket;
import java.util.List;

public interface ProfileService {
  List<Ticket> getAccountTickets(Long accountId);

  void deleteAccountTicketById(Long ticketId);

  List<String> updateAccount(Account account);

  List<PersonalData> getAccountPersonalDatas(Long accountId);

  List<String> updatePersonalData(PersonalData personalData);

  PersonalData getPersonalDataById(Long id);
}
