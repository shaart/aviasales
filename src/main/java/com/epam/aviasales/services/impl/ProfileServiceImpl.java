package com.epam.aviasales.services.impl;

import com.epam.aviasales.domain.Account;
import com.epam.aviasales.domain.PersonalData;
import com.epam.aviasales.domain.Ticket;
import com.epam.aviasales.repositories.AccountRepository;
import com.epam.aviasales.repositories.PersonalDataRepository;
import com.epam.aviasales.repositories.TicketRepository;
import com.epam.aviasales.repositories.impl.AccountRepositoryImpl;
import com.epam.aviasales.repositories.impl.PersonalDataRepositoryImpl;
import com.epam.aviasales.repositories.impl.TicketRepositoryImpl;

import com.epam.aviasales.services.ProfileService;
import java.util.ArrayList;
import java.util.List;

public class ProfileServiceImpl implements ProfileService {
  private static volatile ProfileServiceImpl instance;
  private static final TicketRepository ticketRepository = TicketRepositoryImpl.getInstance();
  private static final AccountRepository accountRepository = AccountRepositoryImpl.getInstance();
  private static final PersonalDataRepository personalDataRepository = PersonalDataRepositoryImpl.getInstance();

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
    ticketRepository.deleteTicket(ticketId);
  }

  public List<String> updatePersonalData(PersonalData personalData){
    List<String> errorMessages = new ArrayList<>();

    PersonalData personalDataFromDB = personalDataRepository.getPersonalDataByPassport(personalData.getPassport());

    if(personalDataFromDB != null && personalDataFromDB.getId() != personalData.getId()){
      errorMessages.add("profile.error.passportExists");
    }
    else{
      personalDataRepository.updatePersonalDataById(personalData);
    }
    return errorMessages;
  }
  /*Todo Exception if null*/
  public PersonalData getPersonalDataById(Long id){
    PersonalData personalData = personalDataRepository.getPersonalDataById(id);
    if(personalData == null)
    {
      //Exception
    }
    return personalData;
  }
}
