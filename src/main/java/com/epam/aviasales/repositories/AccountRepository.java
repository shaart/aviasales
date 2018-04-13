package com.epam.aviasales.repositories;

import com.epam.aviasales.domain.Account;
import com.epam.aviasales.util.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

public class AccountRepository {
  private static AccountRepository instance;

  private AccountRepository() {}

  public void insert(Account account) {
    Session session = HibernateUtil.getSessionFactory().openSession();

    session.beginTransaction();

    session.save(account);
    session.getTransaction().commit();

    session.close();
  }
  /* ToDo: change return value*/
  public Account getAccount(String login) {
    Session session = HibernateUtil.getSessionFactory().openSession();
    Query query = session.createQuery("from Account WHERE login = :login");
    query.setParameter("login", login);
    List list = query.list();
    return list.isEmpty() ? new Account() : (Account) list.get(0);
  }

  public static synchronized AccountRepository getInstance() {
    if (instance == null) {
      instance = new AccountRepository();
    }
    return instance;
  }
}
