package com.epam.aviasales.repositories.impl;

import com.epam.aviasales.domain.Account;
import com.epam.aviasales.repositories.AccountRepository;
import com.epam.aviasales.util.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

public class AccountRepositoryImpl implements AccountRepository {
  private static AccountRepositoryImpl instance;

  private AccountRepositoryImpl() {}

  public void insert(Account account) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    session.beginTransaction();
    session.save(account);
    session.getTransaction().commit();
    session.close();
  }

  public List<Account> getAccountByLogin(String login) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    Query query = session.createQuery("from Account WHERE login = :login");
    query.setParameter("login", login);
    return query.list();
  }

  public static AccountRepositoryImpl getInstance() {
    AccountRepositoryImpl localInstance = instance;
    if (localInstance == null) {
      synchronized (AccountRepositoryImpl.class) {
        localInstance = instance;
        if (localInstance == null) {
          instance = localInstance = new AccountRepositoryImpl();
        }
      }
    }
    return localInstance;
  }

  /*ToDo there is no check for row. Add it!, This is a bad way to choose the row*/
  public boolean isExist(String parameter, String row) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    Query query = session.createQuery("from Account WHERE " + row + " = :parameter");
    query.setParameter("parameter", parameter);
    List list = query.list();

    return list.isEmpty() ? false : true;
  }
}
