package com.epam.aviasales.services.impl;

import com.epam.aviasales.domain.Account;
import com.epam.aviasales.domain.Flight;
import com.epam.aviasales.domain.PersonalData;
import com.epam.aviasales.domain.Ticket;
import com.epam.aviasales.exceptions.NoAvailableSeatsForTheFlight;
import com.epam.aviasales.repositories.AccountRepository;
import com.epam.aviasales.repositories.PersonalDataRepository;
import com.epam.aviasales.repositories.TicketRepository;
import com.epam.aviasales.repositories.impl.AccountRepositoryImpl;
import com.epam.aviasales.repositories.impl.PersonalDataRepositoryImpl;
import com.epam.aviasales.repositories.impl.TicketRepositoryImpl;

import com.epam.aviasales.services.FlightService;
import com.epam.aviasales.services.ProfileService;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.log4j.Log4j;
import org.apache.commons.codec.digest.DigestUtils;

@Log4j
public class ProfileServiceImpl implements ProfileService {
  private static volatile ProfileServiceImpl instance;
  private static final TicketRepository ticketRepository = TicketRepositoryImpl.getInstance();
  private static final AccountRepository accountRepository = AccountRepositoryImpl.getInstance();
  private static final PersonalDataRepository personalDataRepository =
      PersonalDataRepositoryImpl.getInstance();
  private static final FlightService flightService = FlightServiceImpl.getInstance();

  private ProfileServiceImpl() {}

  public static ProfileServiceImpl getInstance() {
    ProfileServiceImpl localInstance = instance;
    if (localInstance == null) {
      synchronized (ProfileServiceImpl.class) {
        localInstance = instance;
        if (localInstance == null) {
          instance = localInstance = new ProfileServiceImpl();
        }
      }
    }
    return localInstance;
  }

  public List<Ticket> getAccountTickets(Long accountId) {
    return ticketRepository.getTicketsByAccountId(accountId);
  }

  public List<String> updateAccount(Account newAccount) {
    List<String> errorMessages = new ArrayList<>();
    Account account = accountRepository.getAccountById(newAccount.getId());

    if (!account.getLogin().equals(newAccount.getLogin())
        && accountRepository.isExist("login", newAccount.getLogin())) {
      errorMessages.add("register.error.login_exist");
    } else if (!account.getEmail().equals(newAccount.getEmail())
        && accountRepository.isExist("email", newAccount.getEmail())) {
      errorMessages.add("register.error.email_exist");
    } else accountRepository.updateAccount(newAccount);
    return errorMessages;
  }

  public List<PersonalData> getAccountPersonalDatas(Long accountId) {
    return ticketRepository.getAccountPersonalDatasByAccountId(accountId);
  }

  public void deleteAccountTicketById(Long ticketId) {
    try {
      Ticket ticket = ticketRepository.getTicketById(ticketId);
      ticketRepository.deleteTicket(ticketId);
      flightService.updateFlight(ticket.getFlight(), ticket.getIsBusiness(), true);
    } catch (NoAvailableSeatsForTheFlight e) {
      log.error(e);
    }
  }

  public List<String> updatePersonalData(PersonalData personalData) {
    List<String> errorMessages = new ArrayList<>();

    PersonalData personalDataFromDB =
        personalDataRepository.getPersonalDataByPassport(personalData.getPassport());

    if (personalDataFromDB != null && !personalDataFromDB.getId().equals(personalData.getId())) {
      errorMessages.add("profile.error.passportExists");
    } else {
      personalDataRepository.updatePersonalDataById(personalData);
    }
    return errorMessages;
  }

  private boolean validateOldPassword(Long id, String oldPassword) {
    Account account = accountRepository.getAccountById(id);
    String sha256hex = DigestUtils.sha256Hex(oldPassword);

    if (sha256hex.equals(account.getPassword())) {
      return true;
    }
    return false;
  }

  public boolean validatePasswords(
      Long id, String oldPassword, String newPassword, String confirmNewPassword) {
    return newPassword.equals(confirmNewPassword) && validateOldPassword(id, oldPassword);
  }

  public void updateAccountPassword(Account account, String newPassword) {
    accountRepository.updateAccountPasswordById(
        account.getId(), DigestUtils.sha256Hex(newPassword));
  }

  /*Todo Exception if null*/
  public PersonalData getPersonalDataById(Long id) {
    PersonalData personalData = personalDataRepository.getPersonalDataById(id);
    if (personalData == null) {
      // Exception
    }
    return personalData;
  }
}
