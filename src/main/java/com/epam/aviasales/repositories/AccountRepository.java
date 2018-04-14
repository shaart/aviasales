package com.epam.aviasales.repositories;

import com.epam.aviasales.domain.Account;
import com.epam.aviasales.util.HibernateUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

public class AccountRepository {

  private static final AccountRepository instance = new AccountRepository();

  public static AccountRepository getInstance() {
    return instance;
  }

  private static final Map<Long, Account> ACCOUNTS_CACHE = new HashMap<>();

  static {
    final int CACHE_COUNT = 100;

    for (int i = 1; i < CACHE_COUNT; i++) {
      ACCOUNTS_CACHE.put(Long.valueOf(i),
          new Account(Long.valueOf(i), i % 30 == 0 ? 2 : (i % 15 == 0 ? 1 : 0),
              i == 15 ? "Bob Marley" : "BOB-" + i, "smartbob" + i, "SHA256-" + i,
              "bob" + i + "@bobworld.com", "123456789"));
    }
  }

  public List<Account> getAccounts() {
    return getAccounts(1, Integer.MAX_VALUE);
  }

  public List<Account> getAccounts(int page, int count) {
    List<Account> airplaneList = new ArrayList<>();
    if (page <= 0 || count <= 0) {
      return airplaneList;
    }

    final int startI = (page - 1) * count;
    for (int i = startI; i < startI + count; i++) {
      if (i >= ACCOUNTS_CACHE.size()) {
        break;
      }
      airplaneList.add(ACCOUNTS_CACHE.get(Long.valueOf(i)));
    }
    return airplaneList;
  }

  public Account getByName(String name) {
    for (Account airplane : ACCOUNTS_CACHE.values()) {
      if (airplane.getName().equals(name)) {
        return airplane;
      }
    }
    return null;
  }

  public Account getById(Long id) {
    return ACCOUNTS_CACHE.get(id);
  }

  public void insert(Account account) {
    Session session = HibernateUtil.getSessionFactory().openSession();

    session.beginTransaction();

    session.save(account);
    session.getTransaction().commit();

    session.close();

  }

  public Account getAccountByLogin(String login) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    Query query = session.createQuery("from Account WHERE login = :pl");
    query.setParameter("pl", login);
    List list = query.list();
    return list.isEmpty() ? new Account() : (Account) list.get(0);
  }
}
